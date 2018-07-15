package com.orai.oraitest.ui.main

import com.orai.oraitest.base.mvp.BasePresenter
import com.orai.oraitest.models.SessionModel

interface MainPresenter: BasePresenter {
    fun onSessionChosen(item: SessionModel)
    fun onNewSessionRequested()
}