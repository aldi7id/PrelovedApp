package com.preloved.app.data.network.model

import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.BaseAuthResponse
import com.preloved.app.data.network.model.response.User
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/register")
    suspend fun postRegisterUser(@Body registerRequest: RegisterRequest): BaseAuthResponse<User, String>

}