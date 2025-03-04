package com.joni.edumart.data.api.request

data class SignupRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val accountType: String? = null,
    val contactNumber: String? = null
    // val otp: String // Uncomment if OTP is required
)