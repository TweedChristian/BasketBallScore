package com.tweedchristian.android.basketballscore.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tweedchristian.android.basketballscore.Game

@Database(entities = [Game::class], version=1)
@TypeConverters(GameTypeConverters::class)
abstract class GameDatabase: RoomDatabase(){
    abstract fun gameDao(): GameDao
}