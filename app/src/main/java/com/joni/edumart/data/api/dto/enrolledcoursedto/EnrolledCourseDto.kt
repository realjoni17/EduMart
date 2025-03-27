package com.joni.edumart.data.api.dto.enrolledcoursedto

import com.google.gson.annotations.SerializedName

data class EnrolledCourseDto(
    @SerializedName("data") val `data`: List<EnrolledData>,
    @SerializedName("success")   val success: Boolean
)