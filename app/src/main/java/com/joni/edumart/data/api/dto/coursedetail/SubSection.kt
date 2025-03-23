package com.joni.edumart.data.api.dto.coursedetail

import com.google.gson.annotations.SerializedName

data class SubSection(
    @SerializedName("__v") val __v: Int,
    @SerializedName("_id")  val _id: String,
    @SerializedName("description")val description: String,
    @SerializedName("timeDuration") val timeDuration: String,
    @SerializedName("title") val title: String
)