package com.joni.edumart

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joni.edumart.data.api.dto.coursedetail.CourseDetailData
import com.joni.edumart.domain.models.Course
import com.joni.edumart.domain.repository.CourseRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(private val courseRepo: CourseRepo) : ViewModel() {
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    private val _courseDetail = MutableStateFlow<CourseDetailState>(CourseDetailState.Idle)
    val courseDetail: StateFlow<CourseDetailState> = _courseDetail

    private val _errorMesssage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMesssage.asStateFlow()


    init {
        loadCourses()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            try {
                val courseList = courseRepo.getCourses()
                _courses.value = courseList
                Log.d(TAG, "load: $courseList")
            } catch (e: Exception) {
                _errorMesssage.value = e.message
                Log.d(TAG, "load:${e.message}")
            }
        }
    }

    fun loadCourseDetails(courseId: String) {
        viewModelScope.launch {
            _courseDetail.value = CourseDetailState.Loading  // Set loading state before making request

            try {
                val courseDetail = courseRepo.getCourseDetails(courseId)
                _courseDetail.value = CourseDetailState.Success(courseDetail)
                Log.d(TAG, "Course loaded: $courseDetail")
            } catch (e: Exception) {
                _courseDetail.value = CourseDetailState.Error(e.message)
                Log.e(TAG, "Error loading course: ${e.message}", e)
            }
        }
    }

}

sealed class CourseDetailState {
    object Idle : CourseDetailState()
    object Loading :  CourseDetailState()
    data class Success(val detail: CourseDetailData) :  CourseDetailState()
    data class Error(val message: String?) :  CourseDetailState()
}