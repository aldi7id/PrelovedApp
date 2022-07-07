package com.preloved.app.ui.fragment.login

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.datasource.user.UserDataSource
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.response.auth.LoginResponse

class LoginRepository(
    private val localDataSource: LocalDataSource,
    private val userDataSource: UserDataSource
): BaseRepositorylmpl(), LoginContract.Repository {
    override suspend fun setTokenSession(datastorePreferences: DatastorePreferences) {
        return localDataSource.setUserSession(datastorePreferences)
    }

    override suspend fun postLoginDataUser(loginRequest: LoginRequest): LoginResponse {
        return userDataSource.postLogin(loginRequest)
    }
}