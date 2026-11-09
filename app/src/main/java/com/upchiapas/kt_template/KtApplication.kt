package com.upchiapas.kt_template

import android.app.Application
import com.upchiapas.kt_template.core.di.AppContainer
import com.upchiapas.kt_template.core.di.DefaultAppContainer

class KtApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}