package com.preloved.app.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
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


//    single {
//        HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//    }
//    single {
//        OkHttpClient.Builder()
//            .addInterceptor {
//                val request = it.request()
//                val queryBuild = request.url
//                    .newBuilder()
//                    .addQueryParameter("token","sdhfhaslfhalksf")
//                    .build()
//                it.proceed(request.newBuilder().url(queryBuild).build())
//            }
//            .build()
//    }
//    single {
//        Retrofit.Builder()
//            .baseUrl("https://market-final-project.herokuapp.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(get())
//            .build()
//    }
//    single {
//        get<Retrofit>().create(PreLovedService::class.java)
//    }
}