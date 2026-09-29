package com.ramaiagent.app

import android.app.Application
import com.ramaiagent.app.data.ConversationRepository
import com.ramaiagent.app.data.ConversationRepositoryImpl
import timber.log.Timber

class RAMApplication : Application() {
    companion object {
        lateinit var conversationRepository: ConversationRepository
    }

    override fun onCreate() {
        super.onCreate()
        
        // Initialize logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        // Initialize repositories
        conversationRepository = ConversationRepositoryImpl()
        
        Timber.d("RAM AI Agent Application initialized")
    }
}
