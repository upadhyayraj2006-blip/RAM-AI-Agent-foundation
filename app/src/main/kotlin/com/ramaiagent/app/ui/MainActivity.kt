package com.ramaiagent.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("MainActivity: onCreate")
        
        setContent {
            RAMTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun RAMTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFFBB86FC),
            primaryContainer = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC6),
            secondaryContainer = Color(0xFF018786),
            background = Color(0xFF121212),
            surface = Color(0xFF1F1F1F),
            error = Color(0xFFCF6679),
            onBackground = Color(0xFFFFFFFF),
            onSurface = Color(0xFFFFFFFF)
        ),
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}

@Composable
fun MainScreen() {
    var agentStatus = remember { mutableStateOf("Ready") }
    var messages = remember { mutableStateOf(listOf<ChatMessage>()) }
    var inputText = remember { mutableStateOf("") }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF121212)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF121212))
        ) {
            // Header
            HeaderSection(agentStatus.value)
            
            // Chat messages
            ChatMessagesSection(
                messages = messages.value,
                modifier = Modifier.weight(1f)
            )
            
            // Input section
            InputSection(
                text = inputText.value,
                onTextChange = { inputText.value = it },
                onSend = {
                    if (inputText.value.isNotBlank()) {
                        messages.value = messages.value + ChatMessage(inputText.value, isUser = true)
                        Timber.d("Message sent: ${inputText.value}")
                        inputText.value = ""
                        agentStatus.value = "Thinking..."
                    }
                },
                onVoiceClick = {
                    Timber.d("Voice input requested")
                    agentStatus.value = "Listening..."
                }
            )
        }
    }
}

@Composable
fun HeaderSection(agentStatus: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1F1F1F))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "RAM AI",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFBB86FC)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Status indicator dot
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = when (agentStatus) {
                            "Ready" -> Color(0xFF03DAC6)
                            "Thinking..." -> Color(0xFFBB86FC)
                            "Listening..." -> Color(0xFF03DAC6)
                            else -> Color(0xFFCF6679)
                        },
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
            
            Text(
                text = "Agent Status: $agentStatus",
                fontSize = 14.sp,
                color = Color(0xFFB0B0B0)
            )
        }
    }
}

@Composable
fun ChatMessagesSection(messages: List<ChatMessage>, modifier: Modifier = Modifier) {
    if (messages.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No messages yet",
                color = Color(0xFF808080),
                fontSize = 14.sp
            )
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            reverseLayout = true,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages.size) { index ->
                val message = messages[messages.size - 1 - index]
                ChatBubble(message)
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .background(
                    color = if (message.isUser) Color(0xFF3700B3) else Color(0xFF1F1F1F),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                ),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
        ) {
            Text(
                text = message.content,
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

@Composable
fun InputSection(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit,
    onVoiceClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1F1F1F))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2C2C2C), androidx.compose.foundation.shape.RoundedCornerShape(24.dp))
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Voice button
            IconButton(
                onClick = onVoiceClick,
                modifier = Modifier.size(36.dp)
            ) {
                Text("🎤", fontSize = 20.sp)
            }
            
            // Text input
            TextField(
                value = text,
                onValueChange = onTextChange,
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Transparent),
                placeholder = {
                    Text(
                        text = "Type a command...",
                        color = Color(0xFF808080),
                        fontSize = 14.sp
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )
            
            // Send button
            IconButton(
                onClick = onSend,
                modifier = Modifier.size(36.dp)
            ) {
                Text("➤", fontSize = 18.sp, color = Color(0xFFBB86FC))
            }
        }
    }
}

data class ChatMessage(
    val content: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)
