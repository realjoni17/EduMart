package com.joni.edumart.domain.repository

import com.joni.edumart.data.api.dto.enrolledcoursedto.EnrolledCourseDto
import com.joni.edumart.data.api.dto.enrolledcoursedto.EnrolledData
import com.joni.edumart.data.api.dto.userdetails.UserData

interface ProfileRepo {

    suspend fun getEnrolledCourses(token : String) : List<EnrolledData>

    suspend fun getUserDetails(token : String) : UserData
}