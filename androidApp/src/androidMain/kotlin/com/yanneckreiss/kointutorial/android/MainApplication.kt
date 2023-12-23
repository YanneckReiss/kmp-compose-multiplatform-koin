package com.yanneckreiss.kointutorial.android

import android.app.Application
import com.yanneckreiss.kointutorial.SharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            androidLogger()

            modules(SharedModule().module)
        }
    }
}
