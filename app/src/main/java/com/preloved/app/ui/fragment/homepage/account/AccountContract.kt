package com.preloved.app.ui.fragment.homepage.account

import androidx.lifecycle.LiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.RegisterResponse
import com.preloved.app.data.network.model.response.UserResponse
import kotlinx.coroutines.flow.Flow

interface AccountContract {
    interface View: BaseContract.BaseView{

    }
    interface ViewModel: BaseContract.BaseViewModel {
        fun userSession()
        fun userSessionResult(): LiveData<DatastorePreferences>
        fun checkLogin() : LiveData<String>
        fun getUserDataResult(): LiveData<Resource<UserResponse>>
        fun getUserData()
        fun deleteToken()
    }

    interface Repository: BaseContract.BaseRepository {
        suspend fun userSession(): Flow<DatastorePreferences>
        suspend fun getUserData(token: String): UserResponse
        suspend fun deleteToken()
    }
}