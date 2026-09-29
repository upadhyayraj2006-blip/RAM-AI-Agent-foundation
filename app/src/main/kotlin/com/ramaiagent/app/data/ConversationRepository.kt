package com.ramaiagent.app.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface ConversationRepository {
    val conversationStateFlow: StateFlow<ConversationState>
    suspend fun sendMessage(content: String)
    suspend fun addAgentResponse(content: String)
    suspend fun setAgentStatus(status: String)
    suspend fun clearConversation()
    suspend fun getConversationHistory(): List<Message>
}

class ConversationRepositoryImpl : ConversationRepository {
    private val _conversationState = MutableStateFlow(ConversationState())
    override val conversationStateFlow: StateFlow<ConversationState> = _conversationState.asStateFlow()

    override suspend fun sendMessage(content: String) {
        val userMessage = Message(
            content = content,
            isUser = true,
            timestamp = System.currentTimeMillis()
        )
        val currentState = _conversationState.value
        _conversationState.value = currentState.copy(
            messages = currentState.messages + userMessage,
            lastInputTimestamp = System.currentTimeMillis()
        )
    }

    override suspend fun addAgentResponse(content: String) {
        val agentMessage = Message(
            content = content,
            isUser = false,
            timestamp = System.currentTimeMillis()
        )
        val currentState = _conversationState.value
        _conversationState.value = currentState.copy(
            messages = currentState.messages + agentMessage,
            isLoading = false
        )
    }

    override suspend fun setAgentStatus(status: String) {
        val currentState = _conversationState.value
        _conversationState.value = currentState.copy(
            agentStatus = status,
            isLoading = status != "Ready"
        )
    }

    override suspend fun clearConversation() {
        _conversationState.value = ConversationState()
    }

    override suspend fun getConversationHistory(): List<Message> {
        return _conversationState.value.messages
    }
}
