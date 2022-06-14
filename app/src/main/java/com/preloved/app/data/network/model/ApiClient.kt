package com.preloved.app.data.network.model

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiClient {
    const val BASE_URL = "https://market-final-project.herokuapp.com/"

    fun getInstance(context: Context) : ApiService{
        val instace: ApiService by lazy {
            val client = OkHttpClient.Builder()
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            retrofit.create(ApiService::class.java)
        }

        return  instace
    }
}