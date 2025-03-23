package com.joni.edumart.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "courses") {

        composable("courses") { CourseListScreen(navController = navController) }
        composable("course/{id}") { backStackEntry ->
            backStackEntry.arguments?.getString("id")?.let { CourseDetailScreen(courseId = it) }
        }

    }
}
