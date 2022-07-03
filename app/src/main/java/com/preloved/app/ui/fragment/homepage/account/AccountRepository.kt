package com.preloved.app.ui.fragment.homepage.account

import androidx.datastore.core.DataStore
import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.ui.fragment.register.RegisterContract
import kotlinx.coroutines.flow.Flow

class AccountRepository(
    private val userDataSource: UserDataSource,
    private val localDataSource: LocalDataSource
): BaseRepositorylmpl(), AccountContract.Repository {

    override suspend fun userSession(): Flow<DatastorePreferences> {
        return localDataSource.getUserSession()
    }



}