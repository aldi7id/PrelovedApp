package com.preloved.app.ui.profile.edit

import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.BaseAuthResponse
import com.preloved.app.data.network.model.response.LoginResponse
import com.preloved.app.data.network.model.response.UserResponse
import java.io.File

interface EditProfileContract {
    interface View :BaseContract.BaseView {
        fun checkFormValidation(): Boolean
        fun setDataToView(data: UserResponse)
        fun getData()
        fun setOnClickListeners()
    }

    interface ViewModel : BaseContract.BaseViewModel {
        fun getChangeProfileResultLiveData(): MutableLiveData<Resource<UserResponse>>
        fun getProfileLiveData(): MutableLiveData<Resource<UserResponse>>
        fun getProfileData()
        fun updateProfileData(userResponse: UserResponse)
        fun logout()
    }

    interface Repository : BaseContract.BaseRepository {
        fun clearSession()
        suspend fun saveCacheProfileData(response: UserResponse)
        suspend fun getProfileData(): UserResponse
        suspend fun updateProfileData(userResponse: UserResponse): UserResponse
    }
}