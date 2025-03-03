package com.joni.edumart.data.api.dto



import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromInstructor(instructor: Instructor?): String? {
        return if (instructor == null) {
            null
        } else {
            val gson = Gson()
            val type = object : TypeToken<Instructor>() {}.type
            gson.toJson(instructor, type)
        }
    }

    @TypeConverter
    fun toInstructor(instructorJson: String?): Instructor? {
        return if (instructorJson == null) {
            null
        } else {
            val gson = Gson()
            val type = object : TypeToken<Instructor>() {}.type
            gson.fromJson(instructorJson, type)
        }
    }

    @TypeConverter
    fun fromRatingAndReviews(ratingAndReviews: List<Any>?): String? {
        return Gson().toJson(ratingAndReviews)
    }

    @TypeConverter
    fun toRatingAndReviews(json: String?): List<Any>? {
        val type = object : TypeToken<List<Any>>() {}.type
        return Gson().fromJson(json, type)
    }
}