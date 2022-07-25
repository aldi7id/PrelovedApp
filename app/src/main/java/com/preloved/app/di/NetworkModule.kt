package com.preloved.app.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.preloved.app.data.network.service.CategoryService
import com.preloved.app.data.network.service.WishlistService
import com.preloved.app.data.network.services.PreLovedService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {
    single { ChuckerInterceptor.Builder(androidContext()).build() }
    single { PreLovedService.invoke(get()) }
    single { CategoryService.invoke(get()) }
    single { WishlistService.invoke(get()) }

}