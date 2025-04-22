package com.joni.edumart.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.joni.edumart.data.api.dto.enrolledcoursedto.EnrolledData
import com.joni.edumart.domain.repository.ProfileRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.joni.edumart.data.api.dto.userdetails.UserData
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepo: ProfileRepo
) : ViewModel() {
    private val _enrolledCoursesState =
        MutableStateFlow<EnrolledCoursesState>(EnrolledCoursesState.Idle)
    val enrolledCoursesState: StateFlow<EnrolledCoursesState> = _enrolledCoursesState.asStateFlow()

    private val _userDetailsState = MutableStateFlow<UserDataState>(UserDataState.Idle)
    val userDetailsState: StateFlow<UserDataState> = _userDetailsState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun fetchEnrolledCourses(token: String) {
        viewModelScope.launch {
            _enrolledCoursesState.value = EnrolledCoursesState.Loading
            _isLoading.value = true
            try {
                val courses = profileRepo.getEnrolledCourses(token)
                _enrolledCoursesState.value = EnrolledCoursesState.Success(courses)
            } catch (e: Exception) {
                _enrolledCoursesState.value =
                    EnrolledCoursesState.Error(e.message ?: "Unknown error")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchUserDetails(token: String) {
        viewModelScope.launch {
            _userDetailsState.value = UserDataState.Loading
            _isLoading.value = true
            try {
                val data = profileRepo.getUserDetails(token)
                _userDetailsState.value = UserDataState.Success(data)
            } catch (e: Exception) {
                _userDetailsState.value =
                    UserDataState.Error(e.message ?: "Unknown Error")
            } finally {
                _isLoading.value = false
            }
        }
    }
}

sealed class EnrolledCoursesState {
    object Idle : EnrolledCoursesState()
    object Loading : EnrolledCoursesState()
    data class Success(val courses: List<EnrolledData>) : EnrolledCoursesState()
    data class Error(val message: String) : EnrolledCoursesState()
}

sealed class UserDataState {
    object Idle : UserDataState()
    object Loading : UserDataState()
    data class Success(val data: UserData) : UserDataState()
    data class Error(val message: String) : UserDataState()
}

