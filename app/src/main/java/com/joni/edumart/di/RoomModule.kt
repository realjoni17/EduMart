package com.joni.edumart.di

import android.content.Context
import androidx.room.Room
import com.joni.edumart.data.offline.AppDatabase
import com.joni.edumart.data.offline.CourseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {


    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "products_database"
        ).build()
    }

    @Provides
    fun provideProductDao(appDatabase: AppDatabase): CourseDao {
        return appDatabase.courseDao()
    }
}