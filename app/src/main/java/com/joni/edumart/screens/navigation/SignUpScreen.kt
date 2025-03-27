package com.joni.edumart.screens.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import com.joni.edumart.presentation.AuthViewModel
import com.joni.edumart.presentation.SignUpState
import kotlinx.coroutines.delay


@Composable
fun SignupScreen(viewModel: AuthViewModel = hiltViewModel(),
                 navController: NavController) {
    val signUpState by viewModel.signupState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign Up", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // Input Fields
        OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("First Name") })
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Last Name") })
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Signup Button
        Button(
            onClick = {
                viewModel.signup(email, password, firstName, lastName, confirmPassword)
                navController.navigate("login")
                Toast.makeText(navController.context, "Sign Up Successful", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !email.isBlank() && !password.isBlank() && !firstName.isBlank() && !lastName.isBlank() && !confirmPassword.isBlank()
        ) {
            Text(text = "Sign Up")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Handle Signup State
        when (signUpState) {
            is SignUpState.Loading -> CircularProgressIndicator()
            is SignUpState.Success -> {
                Text("Signup successful!", color = Color.Green)
                LaunchedEffect(Unit) {
                    delay(1000) // Wait for 1 sec before navigating
                    navController.navigate("login") // Navigate to Login Screen
                    viewModel.resetState() // Reset state to prevent re-triggering success
                }
            }
            is SignUpState.Error -> {
                Text("Error: ${(signUpState as SignUpState.Error).message}", color = Color.Red)
                LaunchedEffect(Unit) {
                    delay(2000)
                    viewModel.resetState()
                }
            }
            is SignUpState.Idle -> {}
        }
    }
}
