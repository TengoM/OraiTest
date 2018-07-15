package com.orai.oraitest

import android.app.Application
import com.orai.oraitest.helper.Constants
import com.orai.oraitest.dagger.components.AppComponent
import com.orai.oraitest.dagger.components.DaggerAppComponent
import com.orai.oraitest.dagger.modules.AppModule
import com.orai.oraitest.dagger.modules.NetModule

class MyApp : Application(){

    companion object {
        const val APP_BASE_URL = Constants.APP_BASE_URL
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule(APP_BASE_URL))
                .build()
    }
}