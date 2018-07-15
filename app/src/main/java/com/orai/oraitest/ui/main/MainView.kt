package com.orai.oraitest.ui.main

import com.orai.oraitest.base.mvp.BaseView
import com.orai.oraitest.models.SessionModel

interface MainView: BaseView {
    fun showError(errorStringRes: Int)
    fun showEmptyLayout()
    fun showFileList(sessionList: List<SessionModel>)
    fun startSessionDetailActivityFor(sessionId: String)
    fun startFloatingSession()

}
