package com.joni.edumart.data.api.dto.coursedetail

data class CourseContent(
    val __v: Int,
    val _id: String,
    val sectionName: String,
    val subSection: List<SubSection>
)