package com.joni.edumart.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.joni.edumart.CourseViewModel
import com.joni.edumart.R
import com.joni.edumart.domain.models.Course
import com.joni.edumart.presentation.components.ShimmerEffect
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun CourseCardShimmer() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 300.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Thumbnail shimmer
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(MaterialTheme.shapes.medium),
                height = 200
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Course title shimmer
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(24.dp),
                height = 24
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Instructor name shimmer
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(20.dp),
                height = 20
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Price shimmer
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(24.dp),
                height = 24
            )
        }
    }
}

@Composable
fun CourseListShimmer() {
    Column {
        // Greeting shimmer
        ShimmerEffect(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(32.dp)
                .padding(16.dp),
            height = 32
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(6) { // Show 6 shimmer cards
                CourseCardShimmer()
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CourseListScreen(vm : CourseViewModel = hiltViewModel(), navController: NavController) {
    val courseList = vm.courses.collectAsState()
    val isLoading = vm.isLoading.collectAsState()

    if (isLoading.value) {
        CourseListShimmer()
    } else {
        Column {
            Text(
                "   Hi User! How are you today?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 300.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(courseList.value) { course ->
                    CourseCard(course = course, onClick = {
                        navController.navigate("course/${course._id}")
                    })
                }
            }
        }
    }
}

@Composable
fun CourseCard(course: Course, onClick : () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 300.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                AsyncImage(
                    model = course.thumbnail,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.baked_goods_1),
                    placeholder = painterResource(id = R.drawable.baked_goods_2)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Course Title
            Text(
                text = course.courseName!!,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Instructor Name
            Text(
                text = "by ${course.instructor!!.firstName} ${course.instructor.lastName}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Rating and Enrolled Students
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

            }

            Spacer(modifier = Modifier.height(8.dp))

            // Price
            Text(
                text = "₹${course.price}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Composable
fun RatingBar(rating: Double) {
    Row {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = if (index < rating) Color(0xFFFFD700) else Color.LightGray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true, showBackground = true)
@Composable
private fun CourseListScreenPrev() {
    //CourseListScreen()
}