package com.joni.edumart.screens
import android.net.Uri
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
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.joni.edumart.data.api.dto.enrolledcoursedto.EnrolledData
import com.joni.edumart.data.api.dto.enrolledcoursedto.SubSection
import com.joni.edumart.domain.repository.ProfileRepo
import com.joni.edumart.presentation.EnrolledCoursesState
import com.joni.edumart.presentation.ProfileViewModel
import com.joni.edumart.presentation.TokenViewModel
import com.joni.edumart.presentation.components.ShimmerEffect

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EnrolledCourses(data: EnrolledData, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = data.courseName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand",
                    modifier = Modifier.size(24.dp)
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    model = data.thumbnail,
                    contentDescription = "Course thumbnail",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Course Progress
                LinearProgressIndicator(
                    progress = (data.progressPercentage / 100f).coerceIn(0f, 1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
                SectionTitle("Course Content")
                LazyColumn(modifier = Modifier.heightIn(min = 100.dp, max = 300.dp)) {
                    items(data.courseContent) { section ->
                        CourseSectionItem(section) { subSection ->
                            val encodedUrl = Uri.encode(subSection.videoUrl)
                            val encodedText = Uri.encode(subSection.title)
                            navController.navigate("player/$encodedUrl/$encodedText")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CourseSectionItem(section: com.joni.edumart.data.api.dto.enrolledcoursedto.CourseContent, onClick : (SubSection) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = section.sectionName,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            section.subSection.forEach { subSection ->
                SubsectionItem(subSection) { onClick(subSection) }
            }
        }
    }
}
@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun EnrolledCourseShimmer() {
    Column {
        // Title shimmer
        ShimmerEffect(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(24.dp)
                .padding(16.dp),
            height = 24
        )

        // Course cards shimmer
        repeat(3) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Course title row shimmer
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ShimmerEffect(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(24.dp),
                            height = 24
                        )
                        ShimmerEffect(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape),
                            height = 24
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Thumbnail shimmer
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        height = 150
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Progress bar shimmer
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        height = 8
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Course content title shimmer
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(24.dp),
                        height = 24
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Course sections shimmer
                    repeat(2) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                // Section title shimmer
                                ShimmerEffect(
                                    modifier = Modifier
                                        .fillMaxWidth(0.6f)
                                        .height(24.dp),
                                    height = 24
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Subsection items shimmer
                                repeat(2) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        ShimmerEffect(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(CircleShape),
                                            height = 24
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Column {
                                            ShimmerEffect(
                                                modifier = Modifier
                                                    .fillMaxWidth(0.8f)
                                                    .height(20.dp),
                                                height = 20
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            ShimmerEffect(
                                                modifier = Modifier
                                                    .fillMaxWidth(0.4f)
                                                    .height(16.dp),
                                                height = 16
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            ShimmerEffect(
                                                modifier = Modifier
                                                    .fillMaxWidth(0.6f)
                                                    .height(16.dp),
                                                height = 16
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Divider()
        }
    }
}

@Composable
fun EnrolledCoursesScreen(vm: ProfileViewModel = hiltViewModel(),
                          navController: NavController,
                          tokenViewModel: TokenViewModel = hiltViewModel()
) {
    val state by vm.enrolledCoursesState.collectAsState()
    val token = tokenViewModel.token.collectAsState().value

    LaunchedEffect(token) {
        if (token != null)
            vm.fetchEnrolledCourses(token)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is EnrolledCoursesState.Loading -> {
                EnrolledCourseShimmer()
            }
            is EnrolledCoursesState.Success -> {
                val courses = (state as EnrolledCoursesState.Success).courses
                Column {
                    Text(
                        text = "Your Courses",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                    LazyColumn {
                        items(courses, key = { it._id }) { course ->
                            EnrolledCourses(course, navController)
                            Divider()  // Add separation between courses
                        }
                    }
                }
            }
            is EnrolledCoursesState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Error loading courses")
                    Button(
                        onClick = { if (token != null)vm.fetchEnrolledCourses(token) },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Retry")
                    }
                }
            }
            EnrolledCoursesState.Idle -> EnrolledCourseShimmer()
        }
    }
}

// Improved SubsectionItem with click handling
@Composable
private fun SubsectionItem(subSection: SubSection, onClick : () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {onClick.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Video",
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = subSection.title)
            Text(
                text = "${subSection.timeDuration.toFloat().toInt()} minutes",  // Better duration formatting
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = subSection.description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}