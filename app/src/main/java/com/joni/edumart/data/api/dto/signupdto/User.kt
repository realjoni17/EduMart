package com.joni.edumart.data.api.dto.signupdto

import com.google.gson.annotations.SerializedName

data class User(
   @SerializedName("__v") val __v: Int,
   @SerializedName("_id")  val _id: String,
   @SerializedName("accountType")  val accountType: String,
   @SerializedName("active") val active: Boolean,
   @SerializedName("additionalDetails") val additionalDetails: String,
   @SerializedName("approved")  val approved: Boolean,
   @SerializedName("courseProgress")  val courseProgress: List<Any>,
   @SerializedName("courses")  val courses: List<Any>,
   @SerializedName("createdAt") val createdAt: String,
   @SerializedName("email")  val email: String,
   @SerializedName("firstName") val firstName: String,
   @SerializedName("image")  val image: String,
   @SerializedName("lastName") val lastName: String,
   @SerializedName("orderedProducts")  val orderedProducts: List<Any>,
   @SerializedName("password")  val password: String,
   @SerializedName("updatedAt")  val updatedAt: String
)