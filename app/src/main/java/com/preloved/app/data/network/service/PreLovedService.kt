package com.preloved.app.data.network.services

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.*
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
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
    suspend fun getUserData(@Header("access_token") token: String): UserResponse

    @PUT("auth/user")
    suspend fun putUserData(
        @Header("access_token") token: String,
        @Body data: RequestBody): UserResponse

    @POST("seller/product")
    suspend fun postProductData(@Header("access_token") token: String,
                                @Body data: RequestBody) : PostProductResponse

    @GET("seller/category")
    suspend fun getCategoryData() : List<CategoryResponseItem>

    @GET("seller/product")
    suspend fun getSellerProduct(@Header("access_token") token: String): List<SellerProductResponseItem>

    @DELETE("seller/product/{id}")
    suspend fun deleteSellerProduct(
        @Header("access_token") token: String,
        @Path("id") id: Int
    ): Response<SellerProductResponseItem>
    @GET("seller/order")
    suspend fun getSellerOrder(@Header("access_token") token: String): List<SellerOrderResponse>
    @GET("seller/order")
    suspend fun getSellerOrderAccepted(@Query("status") status: String): List<SellerOrderResponse>

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