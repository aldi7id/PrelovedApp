package com.preloved.app

import android.app.Application
import com.preloved.app.di.dataSourceModule
import com.preloved.app.di.networkModule
import com.preloved.app.di.repositoryModule
import com.preloved.app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PrelovedApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PrelovedApp)
            modules(
                dataSourceModule,
                networkModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}