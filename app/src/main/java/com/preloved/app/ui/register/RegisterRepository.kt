package com.preloved.app.ui.register

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.model.ApiService
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.BaseAuthResponse
import com.preloved.app.data.network.model.response.User

class RegisterRepository (
        private val apiService: ApiService
        )
//    private val localDataSource: LocalDataSource
: BaseRepositorylmpl(), RegisterContract.Repository {
    override suspend fun checkStatusUser(username: String) {
//        localDataSource.checkUser(username)
    }

//    override suspend fun postRegisterUser(user: RegisterRequest) {
//        //localDataSource.registerUser(userEntity)
//    }
    override suspend fun postRegisterUser(registerRequest: RegisterRequest): BaseAuthResponse<User, String> {
        return apiService.postRegisterUser(registerRequest)
    }
}