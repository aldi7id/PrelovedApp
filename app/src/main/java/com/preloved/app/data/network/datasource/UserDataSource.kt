package com.preloved.app.data.network.datasource

import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.response.LoginResponse

interface UserDataSource {
    suspend fun postLogin(loginRequest: LoginRequest): LoginResponse
}