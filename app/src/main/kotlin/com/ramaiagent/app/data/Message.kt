package com.ramaiagent.app.data

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
data class Message(
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val toolStatus: String? = null,
    val error: String? = null
)

sealed class UiState {
    data object Idle : UiState()
    data object Loading : UiState()
    data class Ready(val messages: List<Message>) : UiState()
    data class Error(val message: String) : UiState()
}

data class ConversationState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val agentStatus: String = "Ready",
    val lastInputTimestamp: Long = 0L
)
