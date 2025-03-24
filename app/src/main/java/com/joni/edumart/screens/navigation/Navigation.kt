package com.joni.edumart.screens.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.joni.edumart.common.Constant
import com.joni.edumart.screens.CourseDetailScreen
import com.joni.edumart.screens.CourseListScreen
import com.joni.edumart.screens.EnrolledCoursesScreen
import com.joni.edumart.screens.PaymentScreen
import com.joni.edumart.screens.VideoPlayerScreen
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val bottomNavItems = listOf(
        BottomNavItem("courses", "Courses", Icons.Default.Home),
        BottomNavItem("enrolled", "Enrolled", Icons.Default.List),
        BottomNavItem("profile", "Profile", Icons.Default.Person)
    )
    val drawerItems = listOf(
        DrawerItem("settings", "Settings", Icons.Default.Settings),
        DrawerItem("help", "Help", Icons.Default.Info),
        DrawerItem("logout", "Logout", Icons.Default.ExitToApp)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route


    val hideUIOnScreens = listOf("player/{videoUrl}/{videoText}", "payment/{courseId}")

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (currentRoute !in hideUIOnScreens)
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)){
                Text("Menu", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp))
                drawerItems.forEach{ items ->
                    DrawerItemView(items) {
                        coroutineScope.launch { drawerState.close() }
                        when(items.route){
                            "logout" -> {}
                            else -> navController.navigate(items.route)
                        }
                    }

                }
            }
        }

    ) {
        Scaffold(
            bottomBar = {
                if (currentRoute !in hideUIOnScreens)
                BottomNavigationBar(
                    navController = navController,
                    items = bottomNavItems
                )
            },
            topBar = {
                if (currentRoute !in hideUIOnScreens)
                TopAppBar(
                    title = {Text("EduMart")},
                    navigationIcon = {
                        IconButton(onClick = {coroutineScope.launch { drawerState.open() }}) {
                            Icon(Icons.Default.Menu, "")
                        }
                    }
                )
            }
        ) {  padding ->

            NavHost(modifier = Modifier.padding(padding).navigationBarsPadding().statusBarsPadding(), navController = navController, startDestination = "courses") {

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

    }
    }

