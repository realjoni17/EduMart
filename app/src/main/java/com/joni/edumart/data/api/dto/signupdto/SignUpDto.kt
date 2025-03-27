package com.joni.edumart.data.api.dto.signupdto

import com.google.gson.annotations.SerializedName

data class SignUpDto(
   @SerializedName("message") val message: String,
   @SerializedName("success") val success: Boolean,
   @SerializedName("user") val user: User?
)