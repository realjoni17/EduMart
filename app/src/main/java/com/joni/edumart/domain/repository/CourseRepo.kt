package com.joni.edumart.domain.repository

import com.joni.edumart.data.api.dto.coursedetail.CourseDetailDto
import com.joni.edumart.data.api.dto.coursedetail.Data
import com.joni.edumart.domain.models.Course

interface CourseRepo {
    suspend fun getCourses() : List<Course>
    suspend fun getCourseDetails(courseId : String) : Data
}