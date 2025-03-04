package com.joni.edumart.di

import android.content.Context
import com.joni.edumart.data.api.ApiService
import com.joni.edumart.data.offline.CourseDao
import com.joni.edumart.data.repoimpl.AuthRepoImpl
import com.joni.edumart.data.repoimpl.CourseRepoImpl
import com.joni.edumart.domain.repository.AuthRepo
import com.joni.edumart.domain.repository.CourseRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Singleton
    @Provides
    fun providesProductRepo(apiService: ApiService, courseDao: CourseDao, @ApplicationContext context : Context): CourseRepo{
        return CourseRepoImpl(apiService, courseDao, context)
    }

    @Singleton
    @Provides
    fun providesAuthRepo(apiService: ApiService): AuthRepo {
        return AuthRepoImpl(apiService)
    }
}