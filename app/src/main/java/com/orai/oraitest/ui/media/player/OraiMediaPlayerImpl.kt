package com.orai.oraitest.ui.media.player

import android.media.MediaPlayer
import com.orai.oraitest.helper.Constants

class OraiMediaPlayerImpl : OraiMediaPlayer {

    private val mediaPlayer = MediaPlayer()

    override fun start(sessionId: String, onCompleterListener: () -> Unit) {
        val filePath = Constants.getFilePathFromSession(sessionId)
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(filePath)
            mediaPlayer.prepare()
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener { onCompleterListener() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun stop() {
        try {
            mediaPlayer.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun isPlaying() = mediaPlayer.isPlaying

}