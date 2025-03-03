package com.joni.edumart.domain.models


data class Course(
    val _id: String,
    val courseName: String?,
    val instructor: Instructor?,
    val price: Int?,
    val ratingAndReviews: List<Any>?,
    val thumbnail: String?
)