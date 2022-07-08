package com.preloved.app.ui.profile.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.UserResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface EditProfileContract {
    interface View :BaseContract.BaseView {
        fun checkFormValidation(): Boolean
        fun setDataToView(data: UserResponse)
        fun getData()
        fun setOnClickListeners()
    }

    interface ViewModel : BaseContract.BaseViewModel {
        fun userSession()
        fun userSessionResult(): LiveData<DatastorePreferences>
        fun getChangeProfileResultLiveData(): MutableLiveData<Resource<UserResponse>>
        fun getProfileLiveData(): MutableLiveData<Resource<UserResponse>>
        fun getProfileData()
        fun updateProfileData(token: String,
                              email: String,
                              nama: String,
                              city: String,
                              address: String,
                              phone: String,
                              profilePhoto: File? = null)
        fun logout()
    }

    interface Repository : BaseContract.BaseRepository {
        suspend fun userSession(): Flow<DatastorePreferences>
        suspend fun getProfileData(token: String): UserResponse
        fun clearSession()
        suspend fun saveCacheProfileData(response: UserResponse)

        suspend fun updateProfileData(email: String,
                                      nama: String,
                                      city: String,
                                      address: String,
                                      phone: String,
                                      token: String,
                                      profilePhoto: File? = null): UserResponse
    }
}