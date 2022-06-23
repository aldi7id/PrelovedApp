package com.preloved.app.ui.splash

import androidx.lifecycle.LiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.data.local.datastore.DatastorePreferences
import kotlinx.coroutines.flow.Flow

interface SplashContract {

    interface View: BaseContract.BaseView {
        fun handlerSplash()
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun splashSession()
        fun splashSessionResult(): LiveData<DatastorePreferences>
    }

    interface Repository: BaseContract.BaseRepository {
        suspend fun splashSession(): Flow<DatastorePreferences>
    }


}