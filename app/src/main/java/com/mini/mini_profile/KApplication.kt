package com.mini.mini_profile

import android.app.Application
import com.mini.mini_profile.di.appModule
import org.koin.core.context.startKoin

class KApplication :Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
    }
}