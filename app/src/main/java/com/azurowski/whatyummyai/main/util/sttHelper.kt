package com.azurowski.whatyummyai.main.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import java.util.Locale

class SttHelper(
    context: Context,
    private val onCommand: (VoiceCommand) -> Unit
) : RecognitionListener {

    enum class VoiceCommand {
        NEXT, PREVIOUS, REPEAT, UNKNOWN
    }

    private var speechRecognizer: SpeechRecognizer? = null
    private var isListeningEnabled = false
    private val recognizerIntent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.forLanguageTag("pl-PL"))
        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
    }

    init {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        speechRecognizer?.setRecognitionListener(this)
    }

    fun startListening() {
        isListeningEnabled = true
        speechRecognizer?.startListening(recognizerIntent)
    }

    fun stopListening() {
        isListeningEnabled = false
        speechRecognizer?.stopListening()
        speechRecognizer?.cancel()
    }

    fun destroy() {
        isListeningEnabled = false
        speechRecognizer?.destroy()
    }

    override fun onError(error: Int) {
        if (isListeningEnabled) {
            startListening()
        }
    }

    override fun onResults(results: Bundle?) {
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        if (matches != null) {
            for (match in matches) {
                val command = parseCommand(match)
                if (command != VoiceCommand.UNKNOWN) {
                    onCommand(command)
                    break
                }
            }
        }
        if (isListeningEnabled) {
            startListening()
        }
    }

    override fun onPartialResults(partialResults: Bundle?) {
        val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        if (matches != null) {
            for (match in matches) {
                val command = parseCommand(match)
                if (command != VoiceCommand.UNKNOWN) {
                    onCommand(command)
                    if (isListeningEnabled) {
                        speechRecognizer?.cancel()
                        startListening()
                    }
                    break
                }
            }
        }
    }

    override fun onReadyForSpeech(params: Bundle?) {}
    override fun onBeginningOfSpeech() {}
    override fun onRmsChanged(rmsdB: Float) {}
    override fun onBufferReceived(buffer: ByteArray?) {}
    override fun onEndOfSpeech() {}
    override fun onEvent(eventType: Int, params: Bundle?) {}

    private fun parseCommand(command: String): VoiceCommand {
        val lowerCommand = command.lowercase(Locale.forLanguageTag("pl-PL"))
        return when {
            lowerCommand.contains("dalej") || lowerCommand.contains("następny") || lowerCommand.contains("następna") -> VoiceCommand.NEXT
            lowerCommand.contains("poprzedni") || lowerCommand.contains("poprzednia") || lowerCommand.contains("wróć") -> VoiceCommand.PREVIOUS
            lowerCommand.contains("powtórz") || lowerCommand.contains("jeszcze raz") -> VoiceCommand.REPEAT
            else -> VoiceCommand.UNKNOWN
        }
    }
}
