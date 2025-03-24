package com.joni.edumart.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.joni.edumart.common.Constant


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "enrolled") {

        composable("courses") { CourseListScreen(navController = navController) }
        composable("course/{id}") { backStackEntry ->
            backStackEntry.arguments?.getString("id")?.let { CourseDetailScreen(courseId = it, navController = navController) }
        }
        composable("payment/{courseId}") { backStackEntry ->
            backStackEntry.arguments?.getString("courseId")?.let {
                val list = listOf(it)
                PaymentScreen(courseIds = list, token = Constant.TOKEN) }
        }
        composable(
            "player/{videoUrl}/{videoText}",
            arguments = listOf(
                navArgument("videoUrl") { type = NavType.StringType },
                navArgument("videoText") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val videoUrl = backStackEntry.arguments?.getString("videoUrl") ?: ""
            val videoText = backStackEntry.arguments?.getString("videoText") ?: ""

            VideoPlayerScreen(
                modifier = Modifier,
                videoUrl = videoUrl,
                videoTitle = videoText
            )
        }
        composable("enrolled") {
            EnrolledCoursesScreen(navController = navController)
        }

    }
}
