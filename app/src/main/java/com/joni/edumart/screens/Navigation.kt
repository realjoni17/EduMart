package com.joni.edumart.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joni.edumart.common.Constant


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "courses") {

        composable("courses") { CourseListScreen(navController = navController) }
        composable("course/{id}") { backStackEntry ->
            backStackEntry.arguments?.getString("id")?.let { CourseDetailScreen(courseId = it, navController = navController) }
        }
        composable("payment/{courseId}") { backStackEntry ->
            backStackEntry.arguments?.getString("courseId")?.let {
                val list = listOf(it)
                PaymentScreen(courseIds = list, token = Constant.TOKEN) }
        }

    }
}
