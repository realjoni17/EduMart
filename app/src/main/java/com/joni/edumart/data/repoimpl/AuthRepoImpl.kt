package com.joni.edumart.data.repoimpl

import android.content.ContentValues.TAG
import android.util.Log
import com.joni.edumart.data.api.ApiService
import com.joni.edumart.data.api.dto.signupdto.User
import com.joni.edumart.data.api.request.LoginRequest
import com.joni.edumart.data.api.request.LoginResponse
import com.joni.edumart.data.api.request.SendOtpRequest
import com.joni.edumart.data.api.request.SignupRequest

import com.joni.edumart.domain.repository.AuthRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(private val apiService: ApiService) : AuthRepo{
    override suspend fun login(email: String, password: String): Flow<LoginResponse> {
        val request = LoginRequest(
            email = email,
            password = password
        )
        try {
            val response = apiService.login(request)
            if (response.isSuccessful) {
                val apiResponse = response.body()
                if ((apiResponse?.success == true)) {
                    return flow { emit(apiResponse) } // return user as flow
                } else {
                    throw Exception(apiResponse?.message ?: "Login failed")
                }
            } else {
                throw Exception(response.errorBody().toString())
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }
    override suspend fun signup(
        email: String,
        password: String,
        firstname: String,
        lastname: String,
        confirmpassword: String
    ): Flow<User> = flow {
        val request = SignupRequest(
            email = email,
            password = password,
            firstName = firstname,
            lastName = lastname,
            confirmPassword = confirmpassword
        )

        val response = apiService.signup(request)

        if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody?.success == true && responseBody.user != null) {
                Log.d(TAG, "signup: ${responseBody.user.user}")
                emit(responseBody.user.user!!)
            } else {
                throw Exception("Signup failed: User data is null")
            }
        } else {
            throw Exception(response.errorBody()?.string() ?: "Unknown error")
        }
    }

    override suspend fun sendOtp(email: String) {
         val request = SendOtpRequest(
            email = email
         )
        try {
            val response = apiService.sendOtp(request)
            if (!response.isSuccessful) {
                throw Exception(response.errorBody().toString())
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }
}
/*

fun LoginResponse.toDomain() : User{ this.user
    return com.joni.edumart.data.api.request.User(
        _id = this.user._id,
        email = this.user.email,
        firstName = this.user.firstName,
        lastName = this.user.lastName,
        accountType = this.user.accountType,
        contactNumber = this.user.token
    )
}*/
