package com.orai.oraitest.base.mvp

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        renderView(savedInstanceState)
    }

    @get:LayoutRes protected abstract val layoutId: Int
    protected abstract fun renderView(savedInstanceState: Bundle?)

}
