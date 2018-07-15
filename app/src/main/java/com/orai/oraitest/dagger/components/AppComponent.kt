package com.orai.oraitest.dagger.components

import com.orai.oraitest.dagger.modules.AppModule
import com.orai.oraitest.dagger.modules.NetModule
import com.orai.oraitest.presenter.MainPresenterImpl
import com.orai.oraitest.presenter.SessionInnerPresenterImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetModule::class
))
open interface AppComponent {
    fun inject(mainPresenterImpl: MainPresenterImpl)
    fun inject(mainPresenterImpl: SessionInnerPresenterImpl)
}