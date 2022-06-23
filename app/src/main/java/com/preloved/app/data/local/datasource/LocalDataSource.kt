package com.preloved.app.data.local.datasource

import com.preloved.app.data.local.datastore.DatastorePreferences
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun setUserSession(datastorePreferences: DatastorePreferences)
    suspend fun getUserSession(): Flow<DatastorePreferences>
    suspend fun deleteUserSession()

}