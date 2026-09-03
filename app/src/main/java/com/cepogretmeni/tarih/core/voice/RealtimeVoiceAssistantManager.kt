package com.cepogretmeni.tarih.core.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import java.util.Locale

/**
 * Gerçek Zamanlı Sesli Tarih Öğretmeni Asistan Motoru (Speech-to-Text & Text-to-Speech)
 * Samimi, nüktedan, erkek sesi tonlamalı ve MEB Maarif Modeli uzmanı.
 */
class RealtimeVoiceAssistantManager(
    private val context: Context,
    private val onSpeechRecognized: (String) -> Unit,
    private val onError: (String) -> Unit
) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var speechRecognizer: SpeechRecognizer? = null
    private var isTtsReady = false

    init {
        tts = TextToSpeech(context, this)
        initSpeechRecognizer()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val localeTr = Locale("tr", "TR")
            val result = tts?.setLanguage(localeTr)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                onError("Türkçe ses motoru cihazda eksik veya desteklenmiyor.")
            } else {
                // Erkek sesi tonu ve sakin/akıcı öğretmen ritmi
                tts?.setPitch(0.88f) // Tok, samimi erkek öğretmen sesi
                tts?.setSpeechRate(0.95f) // Anlaşılır, tane tane konuşma temposu
                isTtsReady = true
            }
        } else {
            onError("Text-to-Speech motoru başlatılamadı.")
        }
    }

    private fun initSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(context)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {}
                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}
                override fun onEndOfSpeech() {}

                override fun onError(error: Int) {
                    val errorMsg = when (error) {
                        SpeechRecognizer.ERROR_NO_MATCH -> "Ses anlaşılamadı, lütfen tekrar deneyin."
                        SpeechRecognizer.ERROR_NETWORK -> "Ağ bağlantı hatası."
                        SpeechRecognizer.ERROR_AUDIO -> "Mikrofon ses kaydı hatası."
                        else -> "Ses tanıma sırasında bir hata oluştu: $error"
                    }
                    onError(errorMsg)
                }

                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    if (!matches.isNullOrEmpty()) {
                        val spokenText = matches[0]
                        onSpeechRecognized(spokenText)
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {}
                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
        }
    }

    /**
     * Mikrofonu dinlemeye başlar
     */
    fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "tr-TR")
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Tarih Öğretmeniniz dinliyor, sorunuzu sorabilirsiniz...")
        }
        speechRecognizer?.startListening(intent)
    }

    /**
     * Dinlemeyi durdurur
     */
    fun stopListening() {
        speechRecognizer?.stopListening()
    }

    /**
     * Tarih Öğretmeni personasıyla sesli yanıt verir
     */
    fun speak(text: String, queueMode: Int = TextToSpeech.QUEUE_FLUSH) {
        if (isTtsReady) {
            tts?.speak(text, queueMode, null, "UTTERANCE_MAARIF_TEACHER")
        }
    }

    fun stopSpeaking() {
        tts?.stop()
    }

    fun destroy() {
        tts?.stop()
        tts?.shutdown()
        speechRecognizer?.destroy()
    }
}
