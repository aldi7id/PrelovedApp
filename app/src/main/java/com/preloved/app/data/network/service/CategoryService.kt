package com.preloved.app.data.network.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface CategoryService {

    @GET("buyer/product")
    suspend fun getCategoryAll(): CategoryResponse

    @GET("buyer/product")
    suspend fun getFilterCategoryByID(@Query("category_id") categoryID: Int): CategoryResponse

    @GET("buyer/product/{id}")
    suspend fun getDetailCategoryById(@Path("id") detailById: Int): CategoryDetailResponse

    companion object {
        @JvmStatic
        operator fun invoke(chuckerInterceptor: ChuckerInterceptor): CategoryService {
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
            return retrofit.create(CategoryService::class.java)
        }
    }

}