package com.joni.edumart.presentation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joni.edumart.domain.repository.TokenRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(private val repo: TokenRepo) : ViewModel() {
    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token.asStateFlow()

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn.asStateFlow()

    init {
        loadToken()
    }

    private fun loadToken() {
        viewModelScope.launch {
            try {
                repo.getToken().collectLatest { storedToken ->
                    _token.value = storedToken
                    _isUserLoggedIn.value = storedToken != null
                    Log.d(TAG, "Token loaded: ${storedToken?.take(10)}...")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading token: ${e.message}")
                _token.value = null
                _isUserLoggedIn.value = false
            }
        }
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            try {
                repo.saveToken(token)
                _token.value = token
                _isUserLoggedIn.value = true
                Log.d(TAG, "Token saved: ${token.take(10)}...")
            } catch (e: Exception) {
                Log.e(TAG, "Error saving token: ${e.message}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                repo.clearToken()
                _token.value = null
                _isUserLoggedIn.value = false
                Log.d(TAG, "User logged out")
            } catch (e: Exception) {
                Log.e(TAG, "Error during logout: ${e.message}")
            }
        }
    }
}
