package com.ramaiagent.app.accessibility

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import timber.log.Timber

class RAMAccessibilityService : AccessibilityService() {
    
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            Timber.d("Accessibility Event: ${it.eventType}")
        }
    }
    
    override fun onInterrupt() {
        Timber.w("Accessibility Service Interrupted")
    }
    
    override fun onServiceConnected() {
        super.onServiceConnected()
        Timber.i("Accessibility Service Connected")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Timber.i("Accessibility Service Destroyed")
    }
}
