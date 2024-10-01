package com.poptato.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PoptatoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}