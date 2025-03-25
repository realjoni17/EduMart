package com.joni.edumart.data.api.request

data class SignupRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val accountType: String? = "Student",
    val contactNumber: String? = null

)