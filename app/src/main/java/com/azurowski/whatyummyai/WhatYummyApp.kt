package com.azurowski.whatyummyai

import android.app.Application
import com.cloudinary.android.MediaManager

class WhatYummyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        val config = mapOf(
            "cloud_name" to "dkma87itz",
            "secure" to true
        )
        MediaManager.init(this, config)
    }
}
