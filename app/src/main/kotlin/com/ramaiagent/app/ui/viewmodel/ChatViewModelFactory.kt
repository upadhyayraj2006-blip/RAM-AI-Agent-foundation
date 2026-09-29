package com.ramaiagent.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ramaiagent.app.data.ConversationRepository

class ChatViewModelFactory(
    private val conversationRepository: ConversationRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(conversationRepository) as T
    }
}
