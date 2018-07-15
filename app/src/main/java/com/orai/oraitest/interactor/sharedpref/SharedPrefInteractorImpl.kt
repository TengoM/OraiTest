package com.orai.oraitest.interactor.sharedpref

import android.app.Application
import android.preference.PreferenceManager

class SharedPrefInteractorImpl(myApp: Application): SharedPrefInteractor {
    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(myApp)
}