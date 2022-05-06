package com.example.address

import android.app.Application
import com.example.address.di.mainModule
import com.example.address.di.networkModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(
                networkModule,
                mainModule
            ))
        }
    }
}