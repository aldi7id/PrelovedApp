package com.preloved.app.ui.register

import androidx.lifecycle.LiveData
import com.bumptech.glide.load.engine.Resource
import com.preloved.app.base.arch.BaseContract

interface RegisterContract {
    interface View: BaseContract.BaseView{
        fun checkFormValidation(): Boolean
    }
    interface ViewModel: BaseContract.BaseViewModel {
        fun getRegisterUserLiveData(): LiveData<Resource<Unit>>
        //fun registerUser(userEntity: UserEntity)
    }

    interface Repository: BaseContract.BaseRepository {
        suspend fun checkStatusUser(username: String)
        //suspend fun postRegisterUser(userEntity: UserEntity)
    }
}