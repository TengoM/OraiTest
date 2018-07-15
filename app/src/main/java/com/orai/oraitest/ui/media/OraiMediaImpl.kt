package com.orai.oraitest.ui.media

import com.orai.oraitest.R
import com.orai.oraitest.ui.media.player.OraiMediaPlayer
import com.orai.oraitest.ui.media.player.OraiMediaPlayerImpl
import com.orai.oraitest.ui.media.recorder.OraiMediaSteamer
import com.orai.oraitest.ui.media.recorder.OraiMediaStreamerImpl
import java.util.*

class OraiMediaImpl(private val mediaCallback: OraiMediaImpl.OraiMediaCallback): OraiMedia {

    private val recorder: OraiMediaSteamer = OraiMediaStreamerImpl()
    private val player: OraiMediaPlayer = OraiMediaPlayerImpl()

    private var timer: Timer? = null

    override fun onRecordClicked(): Int {
        return if (recorder.isRecording()) {
            recorder.stopRecording()
            stopTimer()
            R.string.start_recording
        } else {
            finishPlaying()
            recorder.startRecording()
            startTimerForViewUpdate()
            R.string.stop_recording
        }
    }


    private fun stopTimer(){
        timer?.cancel()
        timer = null
    }

    private fun startTimerForViewUpdate(){
        timer = Timer()
        val task = object : TimerTask() {
            var secs = -1
            override fun run() {
                secs++
                mediaCallback.updateTime(secs)
            }
        }
        timer?.schedule(task, 0, 1000)
    }

    override fun onPlayTextClicked() {
        if(recorder.isReadyToUse()){
            changePlayerState()
        }
    }

    override fun provideSessionId() = recorder.provideSessionId()

    override fun onDestroy() {
        stopTimer()
        finishPlaying()
        recorder.stopRecording()
    }

    private fun changePlayerState(){
        if(player.isPlaying()){
            finishPlaying()
        }else{
            mediaCallback.onPlayStarted()
            player.start(recorder.provideSessionId()!!){
                mediaCallback.onPlayFinished()
            }
        }
    }

    private fun finishPlaying(){
        mediaCallback.onPlayFinished()
        player.stop()
    }

    interface OraiMediaCallback{
        fun updateTime(timeInSecs: Int)
        fun onPlayFinished()
        fun onPlayStarted()
    }

}