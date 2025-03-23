package com.joni.edumart.data.api.dto.coursedetail

import com.google.gson.annotations.SerializedName

data class AdditionalDetails(
    @SerializedName("__v") val __v: Int,
    @SerializedName("_id") val _id: String,
    @SerializedName("about") val about: String,
    @SerializedName("contactNumber") val contactNumber: Long,
    @SerializedName("dateOfBirth") val dateOfBirth: String,
    @SerializedName("gender") val gender: String
)