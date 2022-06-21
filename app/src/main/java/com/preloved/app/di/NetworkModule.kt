package com.preloved.app.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.preloved.app.data.network.model.ApiService
import com.preloved.app.data.network.services.PreLovedService
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://market-final-project.herokuapp.com/"
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
////            .addInterceptor {
////                val request = it.request()
////                val queryBuild = request.url
////                    .newBuilder()
////                    .addQueryParameter("api_key", "00dfa9ebae2c776e7509c85faa9a2e23").build()
////                it.proceed(request.newBuilder().url(queryBuild).build())
////
////            }
//            .build()
//    }
//    single {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(get())
//            .build()
//    }
//    single {
//        get<Retrofit>().create(ApiService::class.java)
//    }
}