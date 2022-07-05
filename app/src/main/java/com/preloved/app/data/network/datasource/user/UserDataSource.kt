package com.preloved.app.data.network.datasource.user

import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.auth.LoginResponse
import com.preloved.app.data.network.model.response.auth.RegisterResponse

interface UserDataSource {
    suspend fun postLogin(loginRequest: LoginRequest): LoginResponse
    suspend fun postRegister(registerRequest: RegisterRequest): RegisterResponse
}