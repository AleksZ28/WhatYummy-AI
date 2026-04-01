package com.azurowski.whatyummyai.main.ui.screens.cooking

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.azurowski.whatyummyai.main.util.TtsHelper

class CookingViewModel(application: Application) : AndroidViewModel(application) {
    private var lastSpokenStep = -1
    private val ttsHelper = TtsHelper(application)

    fun speakStep(stepIndex: Int, text: String) {
        if (stepIndex != lastSpokenStep) {
            ttsHelper.speak(text)
            lastSpokenStep = stepIndex
        }
    }
}