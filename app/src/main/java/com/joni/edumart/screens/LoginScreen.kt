package com.joni.edumart.screens

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.joni.edumart.presentation.AuthViewModel
import com.joni.edumart.presentation.TokenViewModel


@Composable
fun LoginScreen(viewModel: AuthViewModel = hiltViewModel(),
                tokenViewModel: TokenViewModel = hiltViewModel(),
                navController: NavController
                ) {
    val loginState by viewModel.loginState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val token = tokenViewModel.token.collectAsState().value
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Login Button
        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = loginState !is AuthViewModel.LoginState.Loading
        ) {
            Text(text = "Login")
        }


        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = {navController.navigate("signup")}) {
            Text("New User? Register here")
        }

        // Handle Login State
        when (loginState) {
            is AuthViewModel.LoginState.Loading -> {
                CircularProgressIndicator()
            }

            is AuthViewModel.LoginState.Success -> {
               Toast.makeText(LocalContext.current, "Login Successful", Toast.LENGTH_SHORT).show()
                navController.navigate("courses")
                 LaunchedEffect(Unit) {  tokenViewModel.saveToken((loginState as AuthViewModel.LoginState.Success).user.token) }

            }

            is AuthViewModel.LoginState.Error -> {
                Text(text = (loginState as AuthViewModel.LoginState.Error).message ?: "Login Failed", color = Color.Red)
            }

            else -> {}
        }
    }
}

