package com.tweedchristian.android.basketballscore

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.tweedchristian.android.basketballscore.database.GameDatabase
import java.io.File
import java.io.IOException
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "game-database"
private const val TAG = "Repository"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 2

class GameRepository private constructor(
    context: Context,
    private val assets: AssetManager
) {
    private val database : GameDatabase = Room.databaseBuilder(
        context.applicationContext,
        GameDatabase::class.java,
        DATABASE_NAME
    ).build()
    private val sounds: List<Sound>
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUNDS)
        .build()

    private val mediaPlayer: MediaPlayer? = MediaPlayer.create(context,R.raw.sound1)

    init {
        sounds = loadSounds()
    }

    fun playTeamOneSound(){
        mediaPlayer?.start()
    }

    fun playTeamTwoSound(){
        sounds[0].soundId?.let {
            soundPool.play(it, 1.0f,1.0f, 1, 0, 1.0f)
        }
    }

    private val gameDao = database.gameDao()
    private val executor = Executors.newSingleThreadExecutor()
    private val filesDir = context.applicationContext.filesDir

    fun getGames(): LiveData<List<Game>> = gameDao.getGames()
    fun getGame(id: UUID): LiveData<Game?> = gameDao.getGame(id)

    fun addGame(game: Game) {
        executor.execute{
            gameDao.addGame(game)
        }
    }

    fun updateGame(game: Game) {
        executor.execute {
            gameDao.updateGame(game)
        }
    }

    fun getTeamOnePhotoFile(game: Game): File = File(filesDir, game.teamOnePhotoName)

    fun getTeamTwoPhotoFile(game: Game): File = File(filesDir, game.teamTwoPhotoName)

    private fun loadSounds(): List<Sound> {
        val soundNames: Array<String>
        try {
            soundNames = assets.list(SOUNDS_FOLDER)!!
        }
        catch (e: Exception) {
            Log.e(TAG, "Could not list assets", e)
            return emptyList()
        }
        val sounds = mutableListOf<Sound>()
        soundNames.forEach { filename ->
            val assetPath = "$SOUNDS_FOLDER/$filename"
            val sound = Sound(assetPath)
            try {
                load(sound)
                sounds.add(sound)
            }
            catch (e: IOException) {
                Log.e(TAG, "Could not load sound $filename", e)
            }
        }
        return sounds
    }

    private fun load(sound: Sound) {
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }

    companion object {
        private var INSTANCE: GameRepository? = null
        fun initialize(context: Context, assets: AssetManager) {
            if(INSTANCE == null){
                INSTANCE = GameRepository(context, assets)
            }
        }

        fun get(): GameRepository {
            return INSTANCE ?: throw IllegalStateException("GameRepository must be initialized")
        }
    }
}

