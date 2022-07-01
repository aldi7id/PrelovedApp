package com.preloved.app.data.network.services

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.BaseAuthResponse
import com.preloved.app.data.network.model.response.LoginResponse
import com.preloved.app.data.network.model.response.User
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface PreLovedService {
    @POST("auth/login")
    suspend fun postLogin(@Body loginRequest: LoginRequest): LoginResponse

    @POST("auth/register")
    suspend fun postRegisterUser(@Body registerRequest: RegisterRequest): RegisterResponse


//    @GET("auth/user")
//    suspend fun getUser(@Header("access_token") token: String) : BaseAuthResponse<LoginResponse, String>
    @GET("auth/user")
    suspend fun getSyncUser(): UserResponse

    @GET("auth/user")
    suspend fun getUserData(): UserResponse

    @PUT("auth/user")
    suspend fun putUserData(@Body data: RequestBody): UserResponse

    companion object {
        @JvmStatic
        operator fun invoke(chuckerInterceptor: ChuckerInterceptor): PreLovedService{
            val authInterceptor = Interceptor {
                val requestBuilder = it.request().newBuilder()
                requestBuilder.addHeader("access_token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFsZGkyQGdtYWlsLmNvbSIsImlhdCI6MTY1NTgyMTYyMn0.WQvY0x0z7Bj6Qp4uQHFDPK23OjMKilnwHo5OB06iyM4")
                it.proceed(requestBuilder.build())
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
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