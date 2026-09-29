package com.ramaiagent.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramaiagent.app.data.ConversationRepository
import com.ramaiagent.app.data.Message
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class ChatViewModel(
    private val conversationRepository: ConversationRepository
) : ViewModel() {

    val conversationStateFlow: StateFlow<com.ramaiagent.app.data.ConversationState>
        get() = conversationRepository.conversationStateFlow

    fun sendUserMessage(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            try {
                Timber.d("User message: $text")
                conversationRepository.sendMessage(text)
                conversationRepository.setAgentStatus("Thinking...")
                
                // Placeholder: In Phase 8, this will call the AI Agent
                // For now, just simulate a response
                simulateAgentThinking()
            } catch (e: Exception) {
                Timber.e(e, "Error sending message")
                conversationRepository.setAgentStatus("Error")
            }
        }
    }

    fun onVoiceInputRequested() {
        viewModelScope.launch {
            Timber.d("Voice input requested")
            conversationRepository.setAgentStatus("Listening...")
            // Phase 3 will implement actual voice recognition
        }
    }

    fun clearConversation() {
        viewModelScope.launch {
            Timber.d("Clearing conversation")
            conversationRepository.clearConversation()
            conversationRepository.setAgentStatus("Ready")
        }
    }

    private suspend fun simulateAgentThinking() {
        // Simulate thinking delay
        kotlinx.coroutines.delay(800)
        conversationRepository.setAgentStatus("Ready")
        conversationRepository.addAgentResponse("I'm ready to help! (AI agent coming in Phase 8)")
    }
}
