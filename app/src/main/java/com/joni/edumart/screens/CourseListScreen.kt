package com.joni.edumart.screens

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
import coil.compose.AsyncImage
import com.joni.edumart.R


// Data Classes
data class Instructor(
    val _id: String,
    val firstName: String,
    val lastName: String,
    val email: String
)

data class Course(
    val _id: String,
    val courseName: String,
    val instructor: Instructor,
    val price: Double,
    val thumbnail: String,
    val rating: Double = 0.0, // Added for demo purposes
    val enrolledStudents: Int = 0
)

// Dummy Data
object SampleData {
    val courses = listOf(
        Course(
            _id = "67b9ad14efb70037eadc061d",
            courseName = "Advanced Web Development",
            instructor = Instructor(
                _id = "67b6ff75603c1feb26dce11b",
                firstName = "Sarah",
                lastName = "Johnson",
                email = "s.johnson@example.com"
            ),
            price = 15.0,
            thumbnail = "https://res.cloudinary.com/driahvrov/image/upload/v1740225916/edu_mart/w1bjon9l77ejp4bzoybk.jpg",
            rating = 4.5,
            enrolledStudents = 2345
        ),
        Course(
            _id = "68c8be25fgc81148fbee172e",
            courseName = "Mobile App Development",
            instructor = Instructor(
                _id = "78d7gg85724d2feb26dce22c",
                firstName = "Mike",
                lastName = "Chen",
                email = "m.chen@example.com"
            ),
            price = 19.99,
            thumbnail = "https://example.com/mobile-app-dev.jpg",
            rating = 4.8,
            enrolledStudents = 1890
        )
    )
}

@Composable
fun CourseListScreen() {
    val courses = remember { SampleData.courses }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 300.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(courses) { course ->
            CourseCard(course = course)
        }
    }
}

@Composable
fun CourseCard(course: Course) {
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
            // Course Thumbnail
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
                text = course.courseName,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Instructor Name
            Text(
                text = "by ${course.instructor.firstName} ${course.instructor.lastName}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Rating and Enrolled Students
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RatingBar(rating = course.rating)

                Text(
                    text = "(${course.enrolledStudents})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
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
    CourseListScreen()
}