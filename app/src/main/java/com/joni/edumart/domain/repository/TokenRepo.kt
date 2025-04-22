package com.joni.edumart.domain.repository

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow

val TOKEN = stringPreferencesKey("token")

interface TokenRepo {

    fun getToken() : Flow<String?>

    suspend fun saveToken(token : String)

    suspend fun clearToken()

}