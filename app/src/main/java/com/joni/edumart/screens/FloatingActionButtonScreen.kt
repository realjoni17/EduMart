package com.joni.edumart.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.waterfallPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joni.edumart.ChatbotViewModel
import com.joni.edumart.UiState

@Composable
fun FloatingActionButtonScreen(modifier: Modifier = Modifier) {

}

/*
@Composable
fun EduMartFloatingActionButton() {
    var showDialog by remember { mutableStateOf(false) }

    // Floating Action Button
    FloatingActionButton(
        onClick = { showDialog = true },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.MailOutline,
            contentDescription = "Chatbot"
        )
    }

    // Dialog (rendered at root level, not inside FAB)
    if (showDialog) {
        ChatbotDialog(onDismiss = { showDialog = false })
    }
}*/@Composable
fun ChatbotUiScreen(vm: ChatbotViewModel = hiltViewModel()) {
    ChatbotUi(vm = vm)
}

@Composable
fun ChatbotUi(vm: ChatbotViewModel, modifier: Modifier = Modifier) {
    val uiState by vm.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            var result by rememberSaveable { mutableStateOf(listOf("Hello! How can I help you?")) }

            // Fixed-size LazyColumn
            LazyColumn(
                modifier = Modifier
                    .height(300.dp) // Fixed height
                    .padding(bottom = 8.dp)
                    .clipToBounds()
            ) {
                items(result) { message ->
                    when (uiState) {
                        is UiState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                        is UiState.Error -> {
                            result = listOf("Error")
                            Text(
                                text = "Error",
                                modifier = Modifier.padding(8.dp),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                        is UiState.Success -> {
                            result = listOf((uiState as UiState.Success).outputText)
                            Text(
                                text = message,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(
                                        MaterialTheme.colorScheme.primaryContainer,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        else -> {
                            Text(
                                text = message,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(
                                        MaterialTheme.colorScheme.primaryContainer,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        }

        // Input area positioned at the bottom, moves with keyboard
        var userMessage by remember { mutableStateOf("") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)

                .imePadding()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = userMessage,
                onValueChange = { userMessage = it },
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),
                placeholder = { Text("Type a message...") },
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = {
                if (userMessage.isNotEmpty()) {
                    vm.sendPrompt(userMessage)
                    userMessage = ""
                }
            }) {
                Icon(Icons.Default.Send, contentDescription = "Send")
            }
        }
    }
}