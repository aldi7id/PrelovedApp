package com.preloved.app.ui.splash

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.local.datastore.DatastorePreferences
import kotlinx.coroutines.flow.Flow

class SplashRepository(
    private val localDataSource: LocalDataSource
): BaseRepositorylmpl(), SplashContract.Repository {
    override suspend fun splashSession(): Flow<DatastorePreferences> {
        return localDataSource.getUserSession()
    }
}