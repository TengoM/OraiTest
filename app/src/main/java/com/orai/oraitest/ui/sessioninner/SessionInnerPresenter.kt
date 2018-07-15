package com.orai.oraitest.ui.sessioninner

import com.orai.oraitest.base.mvp.BasePresenter

interface SessionInnerPresenter: BasePresenter {
    fun onPlayClicked()
    fun onDestroy()
    fun onBackPressed()
}