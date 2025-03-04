package com.joni.edumart.data.api.dto.coursedetail

data class Category(
    val __v: Int,
    val _id: String,
    val courses: List<String>,
    val description: String,
    val name: String
)