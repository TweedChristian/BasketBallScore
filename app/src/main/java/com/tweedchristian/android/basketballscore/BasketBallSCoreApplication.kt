package com.tweedchristian.android.basketballscore

import android.app.Application

class BasketBallSCoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GameRepository.initialize(this)
    }
}