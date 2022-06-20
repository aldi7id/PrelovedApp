package com.preloved.app.ui.register

import androidx.lifecycle.LiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.BaseAuthResponse
import com.preloved.app.data.network.model.response.LoginResponse
import com.preloved.app.data.network.model.response.User

interface RegisterContract {
    interface View: BaseContract.BaseView{
        fun checkFormValidation(): Boolean
    }
    interface ViewModel: BaseContract.BaseViewModel {
        fun getRegisterUserLiveData(): LiveData<Resource<LoginResponse>>
        fun registerUser(registerRequest: RegisterRequest)
    }

    interface Repository: BaseContract.BaseRepository {
        suspend fun checkStatusUser(username: String)
        suspend fun postRegisterUser(registerRequest: RegisterRequest): BaseAuthResponse<LoginResponse, String>
    }
}