package com.joni.edumart.data.api.request

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)