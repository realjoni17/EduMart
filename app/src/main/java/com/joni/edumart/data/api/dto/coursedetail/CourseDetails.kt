package com.joni.edumart.data.api.dto.coursedetail

data class CourseDetails(
    val __v: Int,
    val _id: String,
    val category: Category,
    val courseContent: List<CourseContent>,
    val courseDescription: String,
    val courseName: String,
    val createdAt: String,
    val instructions: List<String>,
    val instructor: Instructor,
    val price: Int,
    val ratingAndReviews: List<RatingAndReview>,
    val status: String,
    val studentsEnroled: List<String>,
    val tag: List<String>,
    val thumbnail: String,
    val whatYouWillLearn: String
)