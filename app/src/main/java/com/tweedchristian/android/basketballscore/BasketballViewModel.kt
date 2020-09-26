package com.tweedchristian.android.basketballscore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.lang.Math.abs
import java.util.*

private const val TAG = "BasketballViewModel"

class BasketballViewModel: ViewModel() {
    private val gameRepository = GameRepository.get()
    private val gameIdLiveData = MutableLiveData<UUID>()

    var gameLiveData: LiveData<Game?> =
        Transformations.switchMap(gameIdLiveData) { gameId ->
            gameRepository.getGame(gameId)
        }

    fun loadGameById(id: UUID) {
        gameIdLiveData.value = id
    }

    fun saveGame(game: Game) {
        gameRepository.updateGame(game)
    }
    fun setupRepo(size: Int) {
        for(i in 0..size) {
            val random = Random()
            val randomScore = kotlin.math.abs(random.nextInt() % 100)
            val randomScore2 = kotlin.math.abs(random.nextInt() % 100)
            val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
            val randomString1: String = List(20) { alphabet.random() }.joinToString("")
            val randomString2: String = List(20) { alphabet.random() }.joinToString("")
            val uuid: UUID = UUID.randomUUID()
            val game: Game = Game(uuid, randomString1, randomString2, randomScore, randomScore2, Date())
            gameRepository.addGame(game)
        }
    }
}