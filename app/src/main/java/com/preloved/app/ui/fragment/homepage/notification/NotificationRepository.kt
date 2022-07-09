package com.preloved.app.ui.fragment.homepage.notification

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.response.NotificationResponse
import com.preloved.app.ui.fragment.homepage.sell.preview.PreviewProductContract
import kotlinx.coroutines.flow.Flow

class NotificationRepository(private val userDataSource: UserDataSource,
                             private val localDataSource: LocalDataSource
): BaseRepositorylmpl(), NotificationContract.Repository {
    override suspend fun getNotification(token: String) : List<NotificationResponse> {
        return userDataSource.getNotification(token)
    }

    override suspend fun userSession(): Flow<DatastorePreferences> {
        return localDataSource.getUserSession()
    }
}