package com.preloved.app.ui.fragment.register

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.RegisterResponse

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
    override suspend fun postRegisterUser(registerRequest: RegisterRequest): RegisterResponse {
        return userDataSource.postRegister(registerRequest)
    }
}