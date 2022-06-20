package com.preloved.app.ui.register

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.ApiService
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.BaseAuthResponse
import com.preloved.app.data.network.model.response.LoginResponse
import com.preloved.app.data.network.model.response.User

class RegisterRepository (
    private val userDataSource: UserDataSource
        )
//    private val localDataSource: LocalDataSource
: BaseRepositorylmpl(), RegisterContract.Repository {
    override suspend fun checkStatusUser(username: String) {
//        localDataSource.checkUser(username)
    }

//    override suspend fun postRegisterUser(user: RegisterRequest) {
//        //localDataSource.registerUser(userEntity)
//    }
    override suspend fun postRegisterUser(registerRequest: RegisterRequest): BaseAuthResponse<LoginResponse, String> {
        return userDataSource.postRegister(registerRequest)
    }
}