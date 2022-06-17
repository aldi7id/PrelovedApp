package com.preloved.app.ui.login

import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.response.LoginResponse

class LoginRepository(
    private val userDataSource: UserDataSource
): LoginContract.Repository {
    override suspend fun postLoginDataUser(loginRequest: LoginRequest): LoginResponse {
        return userDataSource.postLogin(loginRequest)
    }
}