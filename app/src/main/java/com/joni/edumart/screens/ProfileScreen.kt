package com.joni.edumart.screens


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.joni.edumart.data.api.dto.userdetails.UserData
import com.joni.edumart.presentation.ProfileViewModel
import com.joni.edumart.presentation.TokenViewModel
import com.joni.edumart.presentation.UserDataState
import com.joni.edumart.presentation.components.ShimmerEffect


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(data : UserData) {

    val lastname : String? = data.lastName ?: ""
    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageUrl = data.image.takeIf { it.isEmpty() } ?: ""
            AsyncImage(
                model = imageUrl,
                contentDescription = "Profile Photo",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${data.firstName}  ${lastname}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = data.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (data.courses.isNotEmpty()) {
                   Text(
                       text = "Enrolled Courses",
                       style = MaterialTheme.typography.titleMedium,
                       fontWeight = FontWeight.Bold
                   )
                Spacer(modifier = Modifier.height(8.dp))
                Text("You have Enrolled in ${data.courses.size} courses")

            }
        }

    }
}

@Composable
fun ProfileShimmer() {
    Scaffold() { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile image shimmer
            ShimmerEffect(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                height = 100
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Name shimmer
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(30.dp),
                height = 30
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Email shimmer
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(20.dp),
                height = 20
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Enrolled courses shimmer
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(25.dp),
                height = 25
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Course count shimmer
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(20.dp),
                height = 20
            )
        }
    }
}

@Composable
fun ProfileScreen(vm : ProfileViewModel = hiltViewModel(),
                  navController: NavController,
                  tokenViewModel: TokenViewModel = hiltViewModel()) {
    val state by vm.userDetailsState.collectAsState()
   val token = tokenViewModel.token.collectAsState().value
    LaunchedEffect(token) {
       if (token != null)
        vm.fetchUserDetails(token)

    }
    Log.d(TAG, "ProfileScreen: $state")
    
    when (state) {
        is UserDataState.Loading -> {
            ProfileShimmer()
        }
        is UserDataState.Error -> {
            val error = (state as UserDataState.Error).message
            Text("Error While Fetching User = $error")
        }
        is UserDataState.Success -> {
            val data = (state as UserDataState.Success).data
            Profile(data = data)
        }
        else -> {}
    }
}