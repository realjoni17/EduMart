package com.joni.edumart.presentation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joni.edumart.data.api.dto.signupdto.SignUpDto
import com.joni.edumart.data.api.dto.signupdto.User
import com.joni.edumart.data.api.request.LoginResponse

import com.joni.edumart.domain.repository.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepo: AuthRepo) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _signupState = MutableStateFlow<SignUpState>(SignUpState.Idle())
    val signupState: StateFlow<SignUpState> = _signupState.asStateFlow()

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





    fun signup(
        email: String,
        password: String,
        firstname: String,
        lastname: String,
        confirmpassword: String
    ) {
        viewModelScope.launch {
            try {
                _signupState.value = SignUpState.Loading // Set loading state

                authRepo.signup(email, password, firstname, lastname, confirmpassword)
                    .collect { user : User ->
                        _signupState.value = SignUpState.Success(user)
                    }

            } catch (e: Exception) {
                Log.e("SignupViewModel", "Signup Error: ${e.message}")
                _signupState.value = SignUpState.Error(e.message ?: "Something went wrong")
            }
        }
    }



    fun resetState() {
            _signupState.value = SignUpState.Idle() // Ensure correct idle state
        }




    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val user: LoginResponse) : LoginState()
        data class Error(val message: String?) : LoginState()
    }


}
sealed class SignUpState{
    class Idle : SignUpState()
    object Loading : SignUpState()
    data class Success(val user: User) : SignUpState()
    data class Error(val message: String?) : SignUpState()
}