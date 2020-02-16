package com.saltserv.mvrx

import android.app.Application
import com.airbnb.mvrx.MvRx
import com.airbnb.mvrx.MvRxViewModelConfigFactory

class MvRxApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        MvRx.viewModelConfigFactory = MvRxViewModelConfigFactory(applicationContext)
    }
}