package com.joni.edumart.domain.repository

import com.joni.edumart.domain.models.Course

interface CourseRepo {
    suspend fun getCourses() : List<Course>
}