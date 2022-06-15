package com.preloved.app.ui.register

import com.preloved.app.base.arch.BaseRepositorylmpl

class RegisterRepository ()
//    private val localDataSource: LocalDataSource
: BaseRepositorylmpl(), RegisterContract.Repository {
    override suspend fun checkStatusUser(username: String) {
//        localDataSource.checkUser(username)
    }

//    override suspend fun postRegisterUser(userEntity: UserEntity) {
//        localDataSource.registerUser(userEntity)
//    }
}