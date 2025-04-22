package com.joni.edumart.screens.navigation

  sealed class Screen(val route : String) {
      object Courses : Screen("courses")
      object Enrolled : Screen("enrolled")
      data class CourseDetail(val id : String) : Screen("course/$id")
      data class Payment(val courseId : String) : Screen("payment/$courseId")
      data class VideoPlayer(val videoUrl : String, val videoText : String) : Screen("player")
      object Profile : Screen("profile")
     // object Settings : Screen("settings")
    //  object Help : Screen("help")
      object Settings : Screen("settings")
      object Help : Screen("help")
      object About : Screen("about")
      object ChangePassword : Screen("change_password")
  }