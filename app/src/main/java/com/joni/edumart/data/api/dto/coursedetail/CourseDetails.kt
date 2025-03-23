package com.joni.edumart.data.api.dto.coursedetail

import com.google.gson.annotations.SerializedName

data class CourseDetails(
   @SerializedName("__v") val __v: Int,
   @SerializedName("_id") val _id: String,
   @SerializedName("category") val category: Category,
   @SerializedName("courseContent") val courseContent: List<CourseContent>,
   @SerializedName("courseDescription") val courseDescription: String,
   @SerializedName("courseName") val courseName: String,
   @SerializedName("createdAt") val createdAt: String,
   @SerializedName("instruction") val instructions: List<String>,
   @SerializedName("instructor") val instructor: Instructor,
   @SerializedName("price")val price: Int,
   @SerializedName("ratingAndReviews")val ratingAndReviews: List<RatingAndReview>,
   @SerializedName("status")val status: String,
   @SerializedName("studentsEnroled")val studentsEnroled: List<String>,
   @SerializedName("tag")val tag: List<String>,
   @SerializedName("thumbnail") val thumbnail: String,
   @SerializedName("whatYouWillLearn") val whatYouWillLearn: String
)