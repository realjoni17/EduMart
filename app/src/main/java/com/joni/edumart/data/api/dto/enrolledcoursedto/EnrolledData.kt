package com.joni.edumart.data.api.dto.enrolledcoursedto

import com.google.gson.annotations.SerializedName

data class EnrolledData(
    @SerializedName("__v")  val __v: Int,
    @SerializedName("_id")   val _id: String,
    @SerializedName("category")  val category: String,
    @SerializedName("courseContent") val courseContent: List<CourseContent>,
    @SerializedName("courseDescription") val courseDescription: String,
    @SerializedName("courseName")  val courseName: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("instruction") val instructions: List<String>,
    @SerializedName("instructor") val instructor: String,
    @SerializedName("price") val price: Int,
    @SerializedName("progressPercentage") val progressPercentage: Int,
    @SerializedName("ratingAndReviews")  val ratingAndReviews: List<String>,
    @SerializedName("status") val status: String,
    @SerializedName("studentsEnroled")val studentsEnroled: List<String>,
    @SerializedName("tag")val tag: List<String>,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("totalDuration") val totalDuration: String,
    @SerializedName("whatYouWillLearn")val whatYouWillLearn: String
)