package com.orai.oraitest.ui.sessioninner

import com.orai.oraitest.base.mvp.BaseView

interface SessionInnerView: BaseView{
    fun showError()
    fun updateDate(dateStr: String)
    fun updateName(sessionId: String)
    fun updateScore(score: String)
    fun updateNumFrames(numFrames: String)
    fun getSessionId(): String
    fun updatePlayButtonText(playButtonText: Int)
    fun startMainActivity()

}