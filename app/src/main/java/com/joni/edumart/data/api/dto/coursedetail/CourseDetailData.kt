package com.joni.edumart.data.api.dto.coursedetail

import com.google.gson.annotations.SerializedName

data class CourseDetailData(
   @SerializedName("courseDetails") val courseDetails: CourseDetails,
   @SerializedName("totalDuration") val totalDuration: String
)

data class CourseDetailDataResponse(
   @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: CourseDetailData
)