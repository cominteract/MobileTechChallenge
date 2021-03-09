package com.ainsigne.mobiletechchallenge

import android.app.Application
import com.ainsigne.mobiletechchallenge.di.databaseModule
import com.ainsigne.mobiletechchallenge.di.networkModule
import com.ainsigne.mobiletechchallenge.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Application class for initializing koin dependencies
 */
class MobileTechChallengeApp : Application() {
    /**
     * initializes the koin dependencies when application is created
     */
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MobileTechChallengeApp)
            modules(listOf(databaseModule, networkModule,  viewModelModule))
        }
    }
}