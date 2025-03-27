package com.joni.edumart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joni.edumart.domain.repository.TokenRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TokenViewModel @Inject constructor(private val repo: TokenRepo) : ViewModel() {

    // Fetch token from repo and hold it as a StateFlow
    val token: StateFlow<String?> = repo.getToken()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val isUserLoggedIn = token.map { !it.isNullOrEmpty() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun saveToken(token: String) {
        viewModelScope.launch {
            repo.saveToken(token)
        }
    }
    fun clearToken() {
        viewModelScope.launch {
            repo.saveToken("") // Or null if supported
            //token.value = null
        }
    }
}
