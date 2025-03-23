package com.joni.edumart.data.api


import com.joni.edumart.common.ApiResponse
import com.joni.edumart.data.api.dto.CourseDto
import com.joni.edumart.data.api.dto.coursedetail.Data
import com.joni.edumart.data.api.dto.paymentdto.CapturePaymentRequest
import com.joni.edumart.data.api.dto.paymentdto.CapturePaymentResponse
import com.joni.edumart.data.api.dto.paymentdto.SendPaymentSuccessEmailRequest
import com.joni.edumart.data.api.dto.paymentdto.VerifyPaymentRequest
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
    @POST("auth/login")
    suspend fun login(@Body request : LoginRequest): Response<LoginResponse>

    @POST("/auth/signup")
    suspend fun signup(@Body request : SignupRequest): Response<ApiResponse<User>>

    @POST("/auth/sendotp")
    suspend fun sendOtp(@Body request : SendOtpRequest): Response<ApiResponse<Any>>

    @POST("/auth/reset-password")
    suspend fun resetPassword(@Body request : ChangePasswordRequest): Response<ApiResponse<Any>>

    @POST("/auth/reset-password-token")
    suspend fun resetPasswordToken(email: String): Response<Unit>

    /*
    * Profile Endpoints
    * */

    @GET("/profile/getUserDetails")
    suspend fun getUserDetails(): Response<Unit>

    @GET("/profile/getEnrolledCourses")
    suspend fun getEnrolledCourses(): Response<Unit>

    @GET("/profile/instructorDashboard")
    suspend fun getInstructorDashboard(): Response<Unit>

    /*
    * Student Endpoints
    * */
    @POST("/payment/capturePayment")
    suspend fun capturePayment(
        @Header("Authorization") token: String,
        @Body request : CapturePaymentRequest
    ): Response<CapturePaymentResponse>

    @POST("/payment/verifyPayment")
    suspend fun verifyPayment(
        @Header("Authorization") token: String,
        @Body request : VerifyPaymentRequest
    ): Response<ApiResponse<Any>>

    @POST("/payment/sendPaymentSuccessEmail")
    suspend fun sendPaymentSuccessEmail(
        @Header("Authorization") token: String,
        @Body request : SendPaymentSuccessEmailRequest
    ): Response<ApiResponse<Any>>

    /*
    * Course Endpoints
    * */

    @GET("/course/getAllCourses")
    suspend fun getCourses(): Response<List<CourseDto>>

    @POST("/course/getCourseDetails")
    suspend fun getCourseDetails(@Body courseId: String): Response<Data>

    @PUT("/course/editCourse")
    suspend fun editCourse(courseId: String): Response<Unit>

    @GET("/course/showAllCategories")
    suspend fun showAllCategories(): Response<Unit>

    @GET("/course/getFullCourseDetails")
    suspend fun getFullCourseDetails(courseId: String): Response<Unit>

    /*
    * Rating And Reviews
    * */
    @GET("/course/getReviews")
    suspend fun getReviews(courseId: String): Response<Unit>

   /*
   * Setting Api
   * */
    @PUT("/profile/updateDisplayPicture")
    suspend fun updateDisplayPicture(): Response<Unit>

   @PUT("/profile/updateProfile")
    suspend fun updateProfile(): Response<Unit>

    @POST("/auth/changepassword")
    suspend fun updatePassword(): Response<Unit>


}