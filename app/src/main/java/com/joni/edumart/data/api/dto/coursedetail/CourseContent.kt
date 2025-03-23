package com.joni.edumart.data.api.dto.coursedetail

import com.google.gson.annotations.SerializedName

data class CourseContent(
    @SerializedName("__v")  val __v: Int,
    @SerializedName("_id")  val _id: String,
    @SerializedName("sectionName")val sectionName: String,
    @SerializedName("subSection")val subSection: List<SubSection>
)