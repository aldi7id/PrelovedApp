package com.preloved.app.data.local.datasource

import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.data.local.datastore.DatastorePreferences
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(private val datastoreManager: DatastoreManager): LocalDataSource {
    override suspend fun setUserSession(datastorePreferences: DatastorePreferences) {
        return datastoreManager.setUser(datastorePreferences)
    }

    override suspend fun getUserSession(): Flow<DatastorePreferences> {
        return datastoreManager.getUser()
    }

    override suspend fun deleteUserSession() {
        return datastoreManager.deleteUserFromPref()
    }

}