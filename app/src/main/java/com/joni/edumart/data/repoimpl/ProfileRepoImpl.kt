package com.joni.edumart.data.repoimpl

import android.annotation.SuppressLint
import android.util.Log
import com.joni.edumart.common.Constant
import com.joni.edumart.data.api.ApiService
import com.joni.edumart.data.api.dto.enrolledcoursedto.EnrolledData
import com.joni.edumart.data.api.dto.userdetails.UserData
import com.joni.edumart.domain.repository.ProfileRepo
import javax.inject.Inject


class ProfileRepoImpl @Inject constructor(private val apiService: ApiService) : ProfileRepo {
    @SuppressLint("SuspiciousIndentation")
    override suspend fun getEnrolledCourses(token :String): List<EnrolledData> {
     val response = apiService.getEnrolledCourses(token)
        if(response.isSuccessful){
            response.body()?.let {
                return it.data
            }
        }
        throw Exception("Failed to fetch enrolled courses ${response.errorBody()?.string()}")
    }

    override suspend fun getUserDetails(token : String): UserData {
        val response = apiService.getUserDetails(token)
        Log.d("Joni", "getUserDetails: ${response.body()}")
        if(response.isSuccessful) {
       return response.body()!!.userData
        }
        else
            throw Exception("Failed to Fetch User Data")
    }
}