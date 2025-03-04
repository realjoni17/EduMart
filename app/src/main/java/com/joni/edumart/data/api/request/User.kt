package com.joni.edumart.data.api.request

data class User(
    val __v: Int,
    val _id: String,
    val accountType: String,
    val active: Boolean,
    val additionalDetails: AdditionalDetails,
    val approved: Boolean,
    val courseProgress: List<Any>,
    val courses: List<Any>,
    val createdAt: String,
    val email: String,
    val firstName: String,
    val image: String,
    val lastName: String,
    val resetPasswordExpires: String,
    val token: String,
    val updatedAt: String
)