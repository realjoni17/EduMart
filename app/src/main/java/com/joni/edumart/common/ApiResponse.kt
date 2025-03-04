package com.joni.edumart.common

data class ApiResponse<T> (
    val success : Boolean,
    val message : String,
    val data : T? = null,
    val user : T? = null,
    val token : String? = null,
    val otp : String? = null,
    val error : String? = null
)