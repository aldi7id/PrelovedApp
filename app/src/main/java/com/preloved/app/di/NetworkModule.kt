package com.preloved.app.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.preloved.app.data.network.service.CategoryService
import com.preloved.app.data.network.service.PreLovedService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val networkModule = module {
    single { ChuckerInterceptor.Builder(androidContext()).build() }
    single { PreLovedService.invoke(get()) }
    single { CategoryService.invoke(get()) }


}