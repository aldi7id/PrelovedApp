package com.preloved.app.data.network.services

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.LoginResponse
import com.preloved.app.data.network.model.response.RegisterResponse
import com.preloved.app.data.network.model.response.UserResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface PreLovedService {
    //AUTH
    @POST("auth/login")
    suspend fun postLogin(@Body loginRequest: LoginRequest): LoginResponse

    @POST("auth/register")
    suspend fun postRegisterUser(@Body registerRequest: RegisterRequest): RegisterResponse

    @GET("auth/user")
    suspend fun getUserData(): UserResponse

    @GET("auth/user")
    suspend fun getUserData(@Header("access_token") token: String): UserResponse


    @PUT("auth/user")
    suspend fun putUserData(@Body data: RequestBody): UserResponse

    companion object {
        @JvmStatic
        operator fun invoke(chuckerInterceptor: ChuckerInterceptor): PreLovedService{

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chuckerInterceptor)
                .addInterceptor {
                    val req = it.request()
                    val query = req.url
                        .newBuilder()
                        .build()
                    return@addInterceptor it.proceed(req.newBuilder().url(query).build())
                }
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://market-final-project.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(PreLovedService::class.java)
        }
    }
}