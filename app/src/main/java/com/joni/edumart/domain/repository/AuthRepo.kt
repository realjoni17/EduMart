package com.joni.edumart.domain.repository

interface AuthRepo {

    suspend fun login(email: String, password: String): Unit

    suspend fun signup(email: String, password: String): Unit

    suspend fun sendOtp(email: String): Unit

    suspend fun resetPassword(email: String, otp: String, newPassword: String): Unit

    suspend fun resetPasswordToken(email: String): Unit
}