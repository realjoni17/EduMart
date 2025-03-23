package com.joni.edumart.data.api.dto.coursedetail

import com.google.gson.annotations.SerializedName

data class RatingAndReview(
    @SerializedName("__v") val __v: Int,
    @SerializedName("_id") val _id: String,
    @SerializedName("course") val course: String,
    @SerializedName("rating") val rating: Int,
    @SerializedName("review") val review: String,
    @SerializedName("user") val user: String
)