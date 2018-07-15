package com.orai.oraitest.ui.media.recorder

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import com.orai.oraitest.helper.Constants
import okhttp3.*
import okio.ByteString
import org.json.JSONObject


class OraiMediaStreamerImpl : OraiMediaSteamer {

    companion object {
        private const val AUDIO_SAMPLE_RATE = 16000

        private const val NORMAL_CLOSURE_STATUS = 1000
        private const val CHANNEL_CONFIG = AudioFormat.CHANNEL_CONFIGURATION_MONO
        private const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT
    }


    private var webSocket: WebSocket? = null

    private var curSessionId = ""

    private var testAudioRecord: AudioRecord? = null
    private var minBufSize = AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT)

    private var state = State.IDLE

    override fun startRecording(): Boolean {
        state = State.RECORDING
        startStreaming()
        return true
    }

    override fun stopRecording(): Boolean {
        state = State.FINISHED
        testAudioRecord?.release()
        return true
    }

    override fun isRecording() = state == State.RECORDING
    override fun isReadyToUse() = state == State.READY_TO_USE

    override fun provideSessionId() = curSessionId

    private fun startStreaming() {
        val client = OkHttpClient()
        val request = Request.Builder().url(Constants.SOCKET_URL).build()
        val listener = EchoWebSocketListener()
        webSocket = client.newWebSocket(request, listener)
        try {
            client.dispatcher().executorService().shutdown()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onSocketMessage(text: String?) {
        text?.let {
            try {
                val jsonOBj = JSONObject(text)
                val sessionId = jsonOBj.getString("sid")?: ""
                this@OraiMediaStreamerImpl.curSessionId = sessionId
                startRecordStreaming()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun startRecordStreaming() {
        val buffer = ByteArray(minBufSize)
        testAudioRecord = AudioRecord(MediaRecorder.AudioSource.MIC,
                AUDIO_SAMPLE_RATE,
                CHANNEL_CONFIG,
                AUDIO_FORMAT,
                minBufSize * 20)
        testAudioRecord?.startRecording()
        while (state == State.RECORDING) {
            try {
                val size = testAudioRecord!!.read(buffer, 0, buffer.size)
                webSocket?.send(ByteString.of(buffer, 0, size))
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        closeWs()
    }

    private fun closeWs() {
        try {
            testAudioRecord?.release()
        } finally {
            webSocket?.close(NORMAL_CLOSURE_STATUS, null)
            state = State.READY_TO_USE
        }
    }

    private inner class EchoWebSocketListener : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {}
        override fun onMessage(webSocket: WebSocket?, text: String?) {
            onSocketMessage(text)
        }

        override fun onMessage(webSocket: WebSocket?, bytes: ByteString) {}
        override fun onClosing(webSocket: WebSocket, code: Int, reason: String?) {
            closeWs()
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable?, response: Response?) {
            t?.printStackTrace()
        }
    }

    enum class State {
        RECORDING, IDLE, FINISHED, READY_TO_USE
    }

}