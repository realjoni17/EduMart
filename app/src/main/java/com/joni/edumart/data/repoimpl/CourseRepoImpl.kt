package com.joni.edumart.data.repoimpl

import android.content.Context
import com.joni.edumart.data.api.ApiService
import com.joni.edumart.data.api.dto.CourseDto
import com.joni.edumart.data.api.dto.Instructor
import com.joni.edumart.data.isNetworkAvailable
import com.joni.edumart.data.offline.CourseDao
import com.joni.edumart.domain.models.Course
import com.joni.edumart.domain.repository.CourseRepo
import javax.inject.Inject


class CourseRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val courseDao : CourseDao,
    private val context: Context
) : CourseRepo {
    override suspend fun getCourses(): List<Course> {
        return if (isNetworkAvailable(context)) {
            // Fetch from the network if connected
            val response = apiService.getCourses()
            if (response.isSuccessful) {
                val courses = response.body()!!.toDomain() ?: emptyList()

                // Save products to the local database
                courseDao.insertAll(courses.toDo())

                courses
            } else {
                emptyList()
            }
        } else {
            // Fetch from the local database if offline
            courseDao.getAllCourse().toDomain()
        }
    }

}

fun List<CourseDto>.toDomain(): List<Course> {
    return this.map {
        Course(
            _id = it._id,
            courseName = it.courseName,
            instructor = it.instructor!!.toDomain(),
            price = it.price,
            ratingAndReviews = it.ratingAndReviews,
            thumbnail = it.thumbnail
        )
    }
}

fun Instructor.toDomain(): com.joni.edumart.domain.models.Instructor {
    return com.joni.edumart.domain.models.Instructor(
        __v = this.__v,
        _id = this._id,
        email = this.email,
        accountType = this.accountType,
        active = this.active,
        additionalDetails = this.additionalDetails,
        approved = this.approved,
        courseProgress = this.courseProgress,
        courses = this.courses,
        createdAt = this.createdAt,
        firstName = this.firstName,
        image = this.image,
        lastName = this.lastName,
        password = this.password,
        updatedAt = this.updatedAt
    )
}
fun CourseDto.toDomain(): Course {
    return Course(
        _id = this._id,
        courseName = this.courseName,
        instructor = this.instructor!!.toDomain(),
        price = this.price,
        ratingAndReviews = this.ratingAndReviews,
        thumbnail = this.thumbnail
    )
}


fun List<Course>.toDo(): List<CourseDto> {
    return this.map {
        CourseDto(
            _id = it._id,
            courseName = it.courseName,
            instructor = it.instructor!!.toDo(),
            price = it.price,
            ratingAndReviews = it.ratingAndReviews,
            thumbnail = it.thumbnail
        )
    }
}
fun com.joni.edumart.domain.models.Instructor.toDo(): Instructor {
    return Instructor(
        __v = this.__v,
        _id = this._id,
        email = this.email,
        accountType = this.accountType,
        active = this.active,
        additionalDetails = this.additionalDetails,
        approved = this.approved,
        courseProgress = this.courseProgress,
        courses = this.courses,
        createdAt = this.createdAt,
        firstName = this.firstName,
        image = this.image,
        lastName = this.lastName,
        password = this.password,
        updatedAt = this.updatedAt
    )
}