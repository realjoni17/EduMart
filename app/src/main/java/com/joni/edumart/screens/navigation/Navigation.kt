package com.joni.edumart.screens.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.joni.edumart.screens.ChatbotUiScreen
import com.joni.edumart.screens.CourseDetailScreen
import com.joni.edumart.screens.CourseListScreen

import com.joni.edumart.screens.EnrolledCoursesScreen
import com.joni.edumart.screens.LoginScreen
import com.joni.edumart.screens.PaymentScreen
import com.joni.edumart.screens.ProfileScreen
import com.joni.edumart.screens.SplashScreen
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
        BottomNavItem("enrolled", "My Courses", Icons.Default.List),
        BottomNavItem("Chat","chat", Icons.Default.Search),
        BottomNavItem("profile", "Profile", Icons.Default.Person)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route


   val hideUIOnScreens = listOf("player/{videoUrl}/{videoText}", "payment/{courseId}", "course/{id}", "splash", "login")

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (currentRoute !in hideUIOnScreens)
                ModalDrawerSheet(
                    modifier = Modifier.width(250.dp)
                ) {
                    Text(
                        text = "Menu",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(16.dp)
                    )
                    DrawerItemView(DrawerItem("settings", "Settings", Icons.Default.Settings)) {
                        navController.navigate(Screen.Settings.route)
                        coroutineScope.launch { drawerState.close() } // Close drawer after navigation
                    }
                    DrawerItemView(DrawerItem("help", "Help", Icons.Default.Info)) {
                        navController.navigate(Screen.Help.route)
                        coroutineScope.launch { drawerState.close() }
                    }
                    DrawerItemView(DrawerItem("logout", "Logout", Icons.Default.ExitToApp)) {
                        coroutineScope.launch { drawerState.close() }
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
            },
            ) {  padding ->
            NavHost(modifier = Modifier
                .padding(padding)
                .navigationBarsPadding()
                .statusBarsPadding(),
                navController = navController,
                startDestination = "splash") {

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
                composable("profile") {
                    ProfileScreen(navController = navController)
                }
                composable("chat") {
                    ChatbotUiScreen()
                }
                composable("login") {
                    LoginScreen(navController = navController)
                }
                composable("splash") {
                    SplashScreen(navController = navController)
                }
            composable("signup") {
                SignupScreen(navController = navController)
            }
            }}
        }
      }


