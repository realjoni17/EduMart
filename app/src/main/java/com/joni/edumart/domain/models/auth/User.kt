package com.joni.edumart.domain.models.auth

data class User(
    val _id: String? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val contactNumber: String? = null,
    val accountType: String? = null,
    val approved: Boolean? = null,
    val additionalDetails: Profile? = null,
    val image: String? = null
)