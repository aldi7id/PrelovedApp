package com.preloved.app.data.network.datasource.user

import com.preloved.app.data.network.datasource.user.UserDataSource
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.response.LoginResponse
import com.preloved.app.data.network.service.PreLovedService

class UserDataSourcelmpl(private val preLovedService: PreLovedService): UserDataSource {
    override suspend fun postLogin(loginRequest: LoginRequest): LoginResponse {
        return preLovedService.postLogin(loginRequest)
    }
}