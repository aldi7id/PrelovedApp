package com.preloved.app.ui.login

import androidx.lifecycle.LiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.response.LoginResponse

interface LoginContract {
    interface View  {
        fun loginAuthUser()
    }

    interface ViewModel {
        fun postLoginUserResult(): LiveData<Resource<LoginResponse>>
        fun setTokenSessionResult(): LiveData<DatastorePreferences>
        fun postLoginDataUser(loginRequest: LoginRequest)
    }

    interface Repository {
        suspend fun setTokenSession(datastorePreferences: DatastorePreferences)
        suspend fun postLoginDataUser(loginRequest: LoginRequest): LoginResponse
    }
}