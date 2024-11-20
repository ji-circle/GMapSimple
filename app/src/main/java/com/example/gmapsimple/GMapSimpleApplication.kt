package com.example.gmapsimple

import android.app.Application
import com.example.gmapsimple.ui.ServiceLocator

class GMapSimpleApplication : Application() {
    val appContainer = ServiceLocator()

    override fun onCreate() {
        APPINSTANCE = this
        //TODO 파이어베이스 초기화작업

        super.onCreate()
    }

    companion object {
        private var APPINSTANCE: GMapSimpleApplication? = null
        fun getInstance() = APPINSTANCE!!
    }
}