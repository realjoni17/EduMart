package com.joni.edumart.domain.repository

import com.joni.edumart.data.api.request.LoginResponse
import com.joni.edumart.domain.models.auth.User
import kotlinx.coroutines.flow.Flow

interface AuthRepo {

    suspend fun login(email: String, password: String): Flow<LoginResponse>

   // suspend fun signup(email: String, password: String): Flow<User>

    suspend fun sendOtp(email: String): Unit

  //  suspend fun resetPassword(email: String, otp: String, newPassword: String): Unit

   // suspend fun resetPasswordToken(email: String): Unit
    suspend fun signup(
        email: String,
        password: String,
        firstname: String,
        lastname: String,
        confirmpassword: String
    ): Flow<User>
}