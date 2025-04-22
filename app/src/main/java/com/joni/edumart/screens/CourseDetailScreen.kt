package com.joni.edumart.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.joni.edumart.CourseDetailState
import com.joni.edumart.CourseViewModel
import com.joni.edumart.data.api.dto.coursedetail.CourseContent
import com.joni.edumart.data.api.dto.coursedetail.CourseDetailData
import com.joni.edumart.data.api.dto.coursedetail.SubSection
import com.joni.edumart.presentation.PaymentViewModel
import com.joni.edumart.presentation.TokenViewModel
import com.joni.edumart.presentation.components.ShimmerEffect

@Composable
fun CourseDetailShimmer() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            // Thumbnail shimmer
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                height = 200
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Course title shimmer
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(32.dp),
                height = 32
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Course description shimmer
            repeat(3) {
                ShimmerEffect(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    height = 20
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Instructor info shimmer
            Row(verticalAlignment = Alignment.CenterVertically) {
                ShimmerEffect(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape),
                    height = 56
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(24.dp),
                        height = 24
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(20.dp),
                        height = 20
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Course meta info shimmer
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(20.dp),
                        height = 20
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(24.dp),
                        height = 24
                    )
                }

                Column {
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(20.dp),
                        height = 20
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(24.dp),
                        height = 24
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tags shimmer
            Row {
                repeat(3) {
                    ShimmerEffect(
                        modifier = Modifier
                            .width(80.dp)
                            .height(32.dp)
                            .padding(end = 8.dp),
                        height = 32
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // What you'll learn title shimmer
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(28.dp),
                height = 28
            )

            Spacer(modifier = Modifier.height(8.dp))

            // What you'll learn content shimmer
            repeat(4) {
                ShimmerEffect(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    height = 20
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Course sections shimmer
        repeat(3) {
            item {
                // Section title shimmer
                ShimmerEffect(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(28.dp),
                    height = 28
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subsection shimmer
                repeat(2) {
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .padding(start = 16.dp),
                        height = 48
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition", "ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailScreen(vm : CourseViewModel = hiltViewModel(),
                       courseId : String,
                       navController: NavController,
                       token : TokenViewModel = hiltViewModel(),
                       viewModel: PaymentViewModel = hiltViewModel()
) {
    val context = LocalContext.current as Activity
    val courseData by vm.courseDetail.collectAsState()
    val isUserLoggedIn by token.isUserLoggedIn.collectAsState()
    val paymentState by viewModel.paymentState.collectAsState()
    val tokenId = token.token.collectAsState().value
    val courses = listOf(courseId)

    LaunchedEffect(paymentState) {
        if (paymentState is PaymentViewModel.PaymentState.OrderCreated) {
            val data = (paymentState as PaymentViewModel.PaymentState.OrderCreated).data
            startRazorpayPayment(context, data,viewModel ,tokenId!!, courses)
        }
    }

    LaunchedEffect(courseId) {
        vm.loadCourseDetails(courseId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Course Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            Button(modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.capturePayment(tokenId!!, courses)
                }
                ) {
                Text("Buy Now")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        when (courseData) {
            is CourseDetailState.Success -> {
                val course = (courseData as CourseDetailState.Success).detail
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    item {
                        AsyncImage(
                            model = course.courseDetails.thumbnail,
                            contentDescription = "Course thumbnail",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        course.courseDetails.let {
                            Text(
                                text = it.courseName,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        course.courseDetails.let {
                            Text(
                                text = it.courseDescription,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        InstructorInfo(instructor = course.courseDetails.instructor)

                        Spacer(modifier = Modifier.height(16.dp))

                        course.courseDetails.let {
                            CourseMetaInfo(
                                price = it.price,
                                duration = course.totalDuration
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        TagsSection(tags = course.courseDetails.tag)

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "What You'll Learn",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = course.courseDetails.whatYouWillLearn,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    items(course.courseDetails.courseContent) { section ->
                        CourseSection(section = section)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

            }

            is CourseDetailState.Error -> Text("Error")
            CourseDetailState.Idle -> CourseDetailShimmer()
            CourseDetailState.Loading -> CourseDetailShimmer()
        }
    }
}

@Composable
fun InstructorInfo(instructor: com.joni.edumart.data.api.dto.coursedetail.Instructor) {
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
fun CourseSection(section: CourseContent) {
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
    //CourseDetailScreen()
}