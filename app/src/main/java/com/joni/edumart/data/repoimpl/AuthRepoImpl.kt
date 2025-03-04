package com.joni.edumart.data.repoimpl

import com.joni.edumart.data.api.ApiService
import com.joni.edumart.data.api.request.LoginRequest
import com.joni.edumart.data.api.request.LoginResponse
import com.joni.edumart.data.api.request.SendOtpRequest
import com.joni.edumart.data.api.request.SignupRequest
import com.joni.edumart.domain.models.auth.User
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

    override suspend fun signup(email: String,
                                password: String,
                                firstname : String,
                                lastname : String,
                                confirmpassword : String): Flow<User> {
        val request = SignupRequest(
            email = email,
            password = password,
            firstName = firstname,
            lastName = lastname,
            confirmPassword = confirmpassword
        )
        try {
            val response = apiService.signup(request)
            if (response.isSuccessful) {
                val user = response.body()?.data
                if (user != null) {
                    return flow { emit(user) } // return user as flow
                } else {
                    throw Exception("User not found")
                }
            } else {
                throw Exception(response.errorBody().toString())
            }
        } catch (e: Exception) {
            throw Exception(e.message)
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

fun LoginResponse.toDomain() : User{ this.user
    return User(
        _id = this.user._id,
        email = this.user.email,
        firstName = this.user.firstName,
        lastName = this.user.lastName,
        accountType = this.user.accountType,
        contactNumber = this.user.token
    )
}