package com.joni.edumart.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.joni.edumart.R
import com.joni.edumart.presentation.TokenViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(tokenViewModel : TokenViewModel = hiltViewModel(), navController: NavController) {

    LaunchedEffect(tokenViewModel.isUserLoggedIn) {
        delay(2000L)
        if (tokenViewModel.isUserLoggedIn.value) {
            navController.navigate("courses")
        }
        else
            navController.navigate("login")

    }

    Column(modifier = Modifier.fillMaxSize().background(color = androidx.compose.ui.graphics.Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.edumart),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp)
            )
        }
    }

}

@Preview
@Composable
private fun SplashScreenPrev() {
    val navController = rememberNavController()
    SplashScreen(navController = navController)
}