package com.ramaiagent.app

import android.app.Application
import timber.log.Timber

class RAMApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        Timber.d("RAM AI Agent Application initialized")
    }
}
