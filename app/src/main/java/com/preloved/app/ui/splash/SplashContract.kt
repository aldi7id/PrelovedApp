package com.preloved.app.ui.splash

import androidx.lifecycle.LiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.data.local.datastore.DatastorePreferences

interface SplashContract {

    interface View: BaseContract.BaseView {
        fun HandlerSplash()
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun SplashSession()
        fun SplashSessionResult(): LiveData<DatastorePreferences>
    }

}