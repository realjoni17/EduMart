package com.joni.edumart.data.api.dto

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CourseDto(
     @PrimaryKey
    val _id: String,
    val courseName: String?,
    val instructor: Instructor?,
    val price: Int?,
    val ratingAndReviews: List<Any>?,
    val thumbnail: String?
)

data class CourseResponse(
    val success : Boolean,
    val data: List<CourseDto>
)