package com.borjali.android

import android.app.Application

import com.borjali.domain.preference.CleanAppPreferences
import com.facebook.stetho.Stetho

import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var preferences: CleanAppPreferences


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
    }

}
