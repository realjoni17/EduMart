package com.joni.edumart.data.api.dto.coursedetail

data class RatingAndReview(
    val __v: Int,
    val _id: String,
    val course: String,
    val rating: Int,
    val review: String,
    val user: String
)