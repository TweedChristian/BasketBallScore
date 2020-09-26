package com.tweedchristian.android.basketballscore.database

import androidx.room.TypeConverter
import java.util.*

class GameTypeConverters {

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(milliseconds: Long?): Date? {
        return milliseconds?.let{
            Date(it)
        }
    }

    @TypeConverter
    fun fromUUID(id: UUID?): String? {
        return id?.toString()
    }

    @TypeConverter
    fun toUUID(id: String?): UUID? {
        return UUID.fromString(id)
    }
}