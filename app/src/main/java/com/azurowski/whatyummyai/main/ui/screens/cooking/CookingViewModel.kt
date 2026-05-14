package com.azurowski.whatyummyai.main.ui.screens.cooking

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.azurowski.whatyummyai.main.util.SttHelper
import com.azurowski.whatyummyai.main.util.TtsHelper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CookingViewModel(application: Application) : AndroidViewModel(application) {
    private var lastSpokenStep = -1
    private val ttsHelper = TtsHelper(application)
    private var shouldListen = false

    private val _voiceCommand = MutableSharedFlow<SttHelper.VoiceCommand>()
    val voiceCommand = _voiceCommand.asSharedFlow()

    private val sttHelper = SttHelper(application) { command ->
        viewModelScope.launch {
            _voiceCommand.emit(command)
        }
    }

    init {
        ttsHelper.setSpeechStatusListener(
            onStart = {
                sttHelper.stopListening()
            },
            onDone = {
                if (shouldListen) {
                    sttHelper.startListening()
                }
            }
        )
    }

    fun startListening() {
        shouldListen = true
        sttHelper.startListening()
    }

    fun stopListening() {
        shouldListen = false
        sttHelper.stopListening()
    }

    fun speakStep(stepIndex: Int, text: String) {
        if (stepIndex != lastSpokenStep) {
            ttsHelper.speak(text)
            lastSpokenStep = stepIndex
        }
    }

    fun repeatStep(text: String) {
        ttsHelper.speak(text)
    }

    override fun onCleared() {
        super.onCleared()
        ttsHelper.finish()
        sttHelper.destroy()
    }
}