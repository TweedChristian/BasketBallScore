package com.tweedchristian.android.basketballscore.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tweedchristian.android.basketballscore.Game
import java.util.*

@Dao
interface GameDao {
    @Insert
    fun addGame(game: Game)

    @Update
    fun updateGame(game: Game)

    @Query("SELECT * from table_game")
    fun getGames(): LiveData<List<Game>>

    @Query("SELECT * FROM table_game WHERE id=(:id)")
    fun getGame(id: UUID): LiveData<Game?>
}