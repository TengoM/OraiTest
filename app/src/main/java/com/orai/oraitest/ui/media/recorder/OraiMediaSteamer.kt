package com.orai.oraitest.ui.media.recorder

interface OraiMediaSteamer {
    fun startRecording(): Boolean
    fun stopRecording(): Boolean
    fun provideSessionId(): String?
    fun isRecording(): Boolean
    fun isReadyToUse(): Boolean
}

