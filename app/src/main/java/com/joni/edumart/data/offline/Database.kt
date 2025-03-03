package com.joni.edumart.data.offline

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.joni.edumart.data.api.dto.Converters
import com.joni.edumart.data.api.dto.CourseDto



@Database(entities = [CourseDto::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}
