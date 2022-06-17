package com.preloved.app.ui.login

import androidx.lifecycle.LiveData
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.response.LoginResponse

interface LoginContract {
    interface View {
        fun loginAuthUser()
    }

    interface ViewModel {
        fun postLoginUserResult(): LiveData<Resource<LoginResponse>>
        fun postLoginDataUser(loginRequest: LoginRequest)
    }

    interface Repository {
        suspend fun postLoginDataUser(loginRequest: LoginRequest): LoginResponse
    }
}