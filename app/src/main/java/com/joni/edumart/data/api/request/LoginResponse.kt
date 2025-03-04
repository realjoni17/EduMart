package com.joni.edumart.data.api.request

data class LoginResponse(
    val message: String,
    val success: Boolean,
    val token: String,
    val user: User
)