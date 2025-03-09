package com.example.utube

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//class UtubeApp: Application() {
//
//    override fun onCreate() {
//        super.onCreate()
//        startKoin {
//            androidContext(this@UtubeApp)
//            androidLogger()
//
//            modules(appModule)
//        }
//    }
//}


@HiltAndroidApp
class UtubeApp : Application()