package com.joni.edumart.presentation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joni.edumart.data.api.request.LoginResponse
import com.joni.edumart.domain.models.auth.User
import com.joni.edumart.domain.repository.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepo: AuthRepo) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val user = authRepo.login(email, password).first()
                _loginState.value = LoginState.Success(user)
                Log.d(TAG, "login: $user")
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message)
            }
        }
    }

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val user: LoginResponse) : LoginState()
        data class Error(val message: String?) : LoginState()
    }
}