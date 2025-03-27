package com.joni.edumart.data.api.dto.userdetails

import com.google.gson.annotations.SerializedName

data class UserDetailsDto(
    @SerializedName("data") val userData: UserData,
   @SerializedName("message") val message: String,
    @SerializedName("success") val success: Boolean
)