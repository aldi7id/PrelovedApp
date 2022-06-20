package com.preloved.app.data.network.datasource

import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.BaseAuthResponse
import com.preloved.app.data.network.model.response.LoginResponse
import com.preloved.app.data.network.services.PreLovedService

class UserDataSourcelmpl(private val preLovedService: PreLovedService): UserDataSource {
    override suspend fun postLogin(loginRequest: LoginRequest): LoginResponse {
        return preLovedService.postLogin(loginRequest)
    }

    override suspend fun postRegister(registerRequest: RegisterRequest): BaseAuthResponse<LoginResponse, String> {
        return preLovedService.postRegisterUser(registerRequest)
    }
}