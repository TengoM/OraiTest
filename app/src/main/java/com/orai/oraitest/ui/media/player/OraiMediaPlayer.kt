package com.orai.oraitest.ui.media.player

interface OraiMediaPlayer {
    fun start(sessionId: String, onCompleterListener: () -> Unit)
    fun stop()
    fun isPlaying(): Boolean


}