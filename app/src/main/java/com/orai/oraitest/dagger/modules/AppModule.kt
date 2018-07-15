package com.orai.oraitest.dagger.modules

import android.app.Application
import com.orai.oraitest.interactor.sharedpref.SharedPrefInteractor
import com.orai.oraitest.interactor.sharedpref.SharedPrefInteractorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private var mApplication: Application) {

    @Provides
    @Singleton
    fun provideApplication() = mApplication


    @Provides
    @Singleton
    fun provideSharedPrefInteractor(): SharedPrefInteractor = SharedPrefInteractorImpl(mApplication)

}
