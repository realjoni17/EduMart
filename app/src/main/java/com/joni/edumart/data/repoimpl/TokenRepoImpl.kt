package com.joni.edumart.data.repoimpl

import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit

import com.joni.edumart.domain.repository.TOKEN
import com.joni.edumart.domain.repository.TokenRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TokenRepoImpl @Inject constructor(private val dataStore : DataStore<Preferences>) : TokenRepo{
    override fun getToken(): Flow<String?> = dataStore.data.map { preferences ->
        preferences[TOKEN] ?: null
    }

    override suspend fun saveToken(token: String) {
        dataStore.edit {
            it[TOKEN] = token
        }
    }

    override suspend fun clearToken() {
        dataStore.edit {
            it.remove(TOKEN)
        }
    }
}