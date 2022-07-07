package com.preloved.app.ui.fragment.homepage.account

import androidx.lifecycle.LiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.data.local.datastore.DatastorePreferences
import kotlinx.coroutines.flow.Flow

interface AccountContract {
    interface View: BaseContract.BaseView{

    }
    interface ViewModel: BaseContract.BaseViewModel {
        fun userSession()
        fun userSessionResult(): LiveData<DatastorePreferences>
        fun checkLogin() : LiveData<String>
        fun getToken()
    }

    interface Repository: BaseContract.BaseRepository {
        suspend fun userSession(): Flow<DatastorePreferences>
       fun getToken()
    }
}