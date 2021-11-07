package com.yavin.mybustickets

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BusTicketApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}