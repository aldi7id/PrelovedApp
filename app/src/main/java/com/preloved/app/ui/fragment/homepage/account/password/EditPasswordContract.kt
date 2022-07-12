package com.preloved.app.ui.fragment.homepage.account.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.SellerDeleteResponse
import com.preloved.app.data.network.model.response.UpdatePasswordResponse
import com.preloved.app.data.network.model.response.UserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.File

interface EditPasswordContract {
    interface View: BaseContract.BaseView{
        fun checkFormValidation(): Boolean
        fun setOnClickListeners()
    }
    interface ViewModel: BaseContract.BaseViewModel {
        fun userSession()
        fun userSessionResult(): LiveData<DatastorePreferences>
        fun updatePassword(token: String,
                                   oldPassword: String,
                                   newPassword: String,
                                   rePassword: String)
        fun updateResultPassword(): MutableLiveData<Resource<UpdatePasswordResponse>>
    }

    interface Repository: BaseContract.BaseRepository {
        suspend fun userSession(): Flow<DatastorePreferences>
        suspend fun updatePassword(token: String,
                                    oldPassword: String,
                                    newPassword: String,
                                    rePassword: String): UpdatePasswordResponse
    }
}