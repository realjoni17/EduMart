package com.joni.edumart.screens


import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
/*


@Composable
fun ProfileScreen(user: User) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileImage(user.avatarUrl)

        Text(user.name, style = MaterialTheme.typography.bodyMedium)
        Text(user.email)

        if(user.isInstructor) {
            InstructorStats(user.stats.totalStudents, user.stats.totalCourses, user.stats.rating)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { */
/* Edit profile *//*
 }) {
            Text("Edit Profile")
        }

        //  EnrolledCoursesSection(user.enrolledCourses)
    }
}

@Composable
fun ProfileImage(avatarUrl: String) {
    Box {
        */
/* Image(
             painter = rememberAsyncImagePainter(avatarUrl),
             contentDescription = "Profile Picture",
             modifier = Modifier
                 .size(120.dp)
                 .clip(CircleShape)
         )*//*


        IconButton(
            onClick = { */
/* Change photo *//*
 },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Default.Edit, "Change Photo")
        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES, showSystemUi = true, showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_NO, showSystemUi = true, showBackground = true)
@Composable
private fun CourseListScreenPrev() {
    val navController = rememberNavController()
    //LoginScreen(navController = navController)
    ProfileScreen(user = SampleData.user)
}*/
