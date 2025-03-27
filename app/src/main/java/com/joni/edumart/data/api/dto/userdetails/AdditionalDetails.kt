package com.joni.edumart.data.api.dto.userdetails

import com.google.gson.annotations.SerializedName

data class AdditionalDetails(
    @SerializedName("__v")  val __v: Int,
    @SerializedName("_id")  val _id: String,
    @SerializedName("about") val about: Any,
    @SerializedName("contactNumber") val contactNumber: Any,
    @SerializedName("dateOfBirth") val dateOfBirth: Any,
    @SerializedName("gender") val gender: Any
)