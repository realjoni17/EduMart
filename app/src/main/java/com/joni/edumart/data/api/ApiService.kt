package com.joni.edumart.data.api


import com.google.gson.annotations.SerializedName
import com.joni.edumart.common.ApiResponse
import com.joni.edumart.data.api.dto.CourseDto
import com.joni.edumart.data.api.dto.CourseResponse
import com.joni.edumart.data.api.dto.coursedetail.CourseDetailData
import com.joni.edumart.data.api.dto.coursedetail.CourseDetailDataResponse
import com.joni.edumart.data.api.dto.enrolledcoursedto.EnrolledCourseDto
import com.joni.edumart.data.api.dto.paymentdto.CapturePaymentRequest
import com.joni.edumart.data.api.dto.paymentdto.CapturePaymentResponse
import com.joni.edumart.data.api.dto.paymentdto.SendPaymentSuccessEmailRequest
import com.joni.edumart.data.api.dto.paymentdto.VerifyPaymentRequest
import com.joni.edumart.data.api.dto.userdetails.UserDetailsDto
import com.joni.edumart.data.api.request.ChangePasswordRequest
import com.joni.edumart.data.api.request.LoginRequest
import com.joni.edumart.data.api.request.LoginResponse
import com.joni.edumart.data.api.request.SendOtpRequest
import com.joni.edumart.data.api.request.SignupRequest
import com.joni.edumart.domain.models.auth.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT


interface ApiService {
    /*
    * Authentication Endpoints
    * */
    @POST("/api/v1/auth/login")
    suspend fun login(@Body request : LoginRequest): Response<LoginResponse>

    @POST("/api/v1/auth/signup")
    suspend fun signup(@Body request : SignupRequest): Response<ApiResponse<User>>

    @POST("/api/v1/auth/sendotp")
    suspend fun sendOtp(@Body request : SendOtpRequest): Response<ApiResponse<Any>>

    @POST("/api/v1/auth/reset-password")
    suspend fun resetPassword(@Body request : ChangePasswordRequest): Response<ApiResponse<Any>>

    @POST("/api/v1/auth/reset-password-token")
    suspend fun resetPasswordToken(email: String): Response<Unit>

    /*
    * Profile Endpoints
    * */

    @GET("/api/v1/profile/getUserDetails")
    suspend fun getUserDetails(
        @Header("Authorization") token: String
    ): Response<UserDetailsDto>

    @GET("/api/v1/profile/getEnrolledCourses")
    suspend fun getEnrolledCourses(
        @Header("Authorization") token: String
    ): Response<EnrolledCourseDto>

    @GET("/api/v1/profile/instructorDashboard")
    suspend fun getInstructorDashboard(): Response<Unit>

    /*
    * Student Endpoints
    * */
    @POST("/api/v1/payment/capturePayment")
    suspend fun capturePayment(
        @Header("Authorization") token: String,
        @Body request : CapturePaymentRequest
    ): Response<CapturePaymentResponse>

    @POST("/api/v1/payment/verifyPayment")
    suspend fun verifyPayment(
        @Header("Authorization") token: String,
        @Body request : VerifyPaymentRequest
    ): Response<ApiResponse<Any>>

    @POST("/api/v1/payment/sendPaymentSuccessEmail")
    suspend fun sendPaymentSuccessEmail(
        @Header("Authorization") token: String,
        @Body request : SendPaymentSuccessEmailRequest
    ): Response<ApiResponse<Any>>

    /*
    * Course Endpoints
    * */

    @GET("/api/v1/course/getAllCourses")
    suspend fun getCourses(): Response<CourseResponse>

    data class CourseDetailRequest(@SerializedName("courseId") val courseId: String)

    @POST("/api/v1/course/getCourseDetails")
    suspend fun getCourseDetails(@Body request : CourseDetailRequest): Response<CourseDetailDataResponse>

    @PUT("/api/v1/course/editCourse")
    suspend fun editCourse(courseId: String): Response<Unit>

    @GET("/api/v1/course/showAllCategories")
    suspend fun showAllCategories(): Response<Unit>

    @GET("/api/v1/course/getFullCourseDetails")
    suspend fun getFullCourseDetails(courseId: String): Response<Unit>

    /*
    * Rating And Reviews
    * */
    @GET("/api/v1/course/getReviews")
    suspend fun getReviews(courseId: String): Response<Unit>

   /*
   * Setting Api
   * */
    @PUT("/api/v1/profile/updateDisplayPicture")
    suspend fun updateDisplayPicture(): Response<Unit>

   @PUT("/api/v1/profile/updateProfile")
    suspend fun updateProfile(): Response<Unit>

    @POST("/api/v1/auth/changepassword")
    suspend fun updatePassword(): Response<Unit>


}