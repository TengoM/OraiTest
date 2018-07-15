package com.orai.oraitest.ui.media

import android.content.Context
import android.support.annotation.StringRes

interface OraiMedia{

    @StringRes fun onRecordClicked(): Int
    fun onPlayTextClicked()
    fun provideSessionId(): String?
    fun onDestroy()
}