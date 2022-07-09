package com.preloved.app.ui.fragment.homepage.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.NotificationResponse
import com.preloved.app.data.network.model.response.PostProductResponse
import com.preloved.app.data.network.model.response.UserResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface NotificationContract {
    interface View {
        fun setOnClickListeners()
    }

    interface ViewModel {
        fun userSession()
        fun userSessionResult(): LiveData<DatastorePreferences>
        fun getNotification(token: String)
        fun getNotificationResult() : MutableLiveData<Resource<List<NotificationResponse>>>
    }

    interface Repository {
        suspend fun getNotification(token: String) : List<NotificationResponse>
        suspend fun userSession(): Flow<DatastorePreferences>
    }
}