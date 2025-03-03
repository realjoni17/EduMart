package com.joni.edumart.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage

// Data Classes
data class ApiResponse(
    val success: Boolean,
    val data: CourseData
)

data class CourseData(
    val courseDetails: CourseDetails,
    val totalDuration: String
)

data class CourseDetails(
    val _id: String,
    val courseName: String,
    val courseDescription: String,
    val instructor: DummyInstructor,
    val whatYouWillLearn: String,
    val courseContent: List<CourseContentSection>,
    val price: Int,
    val thumbnail: String,
    val tag: List<String>,
    val category: Category,
    val instructions: List<String>,
    val status: String
)

data class DummyInstructor(
    val firstName: String,
    val lastName: String,
    val email: String,
    val image: String
)

data class CourseContentSection(
    val sectionName: String,
    val subSection: List<SubSection>
)

data class SubSection(
    val title: String,
    val timeDuration: String,
    val description: String
)

data class Category(
    val name: String,
    val description: String
)

// Dummy Data
val dummyCourseData = ApiResponse(
    success = true,
    data = CourseData(
        courseDetails = CourseDetails(
            _id = "67b9ad14efb70037eadc061d",
            courseName = "Advanced Web Development",
            courseDescription = "Updated full-stack web development course",
            instructor = DummyInstructor(
                firstName = "John",
                lastName = "Doe",
                email = "john.doe@example.com",
                image = ""
            ),
            whatYouWillLearn = "Learn full-stack with hands-on projects",
            courseContent = listOf(
                CourseContentSection(
                    sectionName = "Introduction",
                    subSection = listOf(
                        SubSection(
                            title = "Getting Started",
                            timeDuration = "1.5",
                            description = "Course overview and setup"
                        )
                    )
                )
            ),
            price = 49,
            thumbnail = "https://example.com/thumbnail.jpg",
            tag = listOf("React", "Next.js", "Node.js"),
            category = Category(
                name = "Web Development",
                description = "Full-stack web development course"
            ),
            instructions = listOf("Focus completely", "Build projects"),
            status = "Published"
        ),
        totalDuration = "15 hours"
    )
)

// Composable Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(courseData: ApiResponse = dummyCourseData) {
    val course = courseData.data.courseDetails

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Course Details") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                AsyncImage(
                    model = course.thumbnail,
                    contentDescription = "Course thumbnail",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = course.courseName,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = course.courseDescription,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                InstructorInfo(instructor = course.instructor)

                Spacer(modifier = Modifier.height(16.dp))

                CourseMetaInfo(price = course.price, duration = courseData.data.totalDuration)

                Spacer(modifier = Modifier.height(16.dp))

                TagsSection(tags = course.tag)

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "What You'll Learn",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = course.whatYouWillLearn,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            items(course.courseContent) { section ->
                CourseSection(section = section)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun InstructorInfo(instructor: DummyInstructor) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Instructor",
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = "${instructor.firstName} ${instructor.lastName}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Instructor",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun CourseMetaInfo(price: Int, duration: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = "Price",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "$$price",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        Column {
            Text(
                text = "Duration",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = duration,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsSection(tags: List<String>) {
    Column {
        Text(
            text = "Tags",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tags.forEach { tag ->
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = tag,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
fun CourseSection(section: CourseContentSection) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = section.sectionName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            section.subSection.forEach { subSection ->
                SubSectionItem(subSection = subSection)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun SubSectionItem(subSection: SubSection) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = subSection.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = subSection.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = "${subSection.timeDuration} hrs",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
private fun CourseDetailScreenPrev() {
    CourseDetailScreen()
}