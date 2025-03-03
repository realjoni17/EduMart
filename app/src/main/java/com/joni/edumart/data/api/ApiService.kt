package com.joni.edumart.data.api


import com.joni.edumart.data.api.dto.CourseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT


interface ApiService {
    /*
    * Authentication Endpoints
    * */
    @GET("/auth/login")
    suspend fun login(email: String, password: String): Response<Unit>

    @POST("/auth/signup")
    suspend fun signup(email: String, password: String): Response<Unit>

    @POST("/auth/sendotp")
    suspend fun sendOtp(email: String): Response<Unit>

    @POST("/auth/reset-password")
    suspend fun resetPassword(email: String, otp: String, newPassword: String): Response<Unit>

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
    suspend fun capturePayment(): Response<Unit>

    @POST("/payment/verifyPayment")
    suspend fun verifyPayment(): Response<Unit>

    @POST("/payment/sendPaymentSuccessEmail")
    suspend fun sendPaymentSuccessEmail(): Response<Unit>

    /*
    * Course Endpoints
    * */

    @GET("/course/getAllCourses")
    suspend fun getCourses(): Response<List<CourseDto>>

    @GET("/course/getCourseDetails")
    suspend fun getCourseDetails(courseId: String): Response<Unit>

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