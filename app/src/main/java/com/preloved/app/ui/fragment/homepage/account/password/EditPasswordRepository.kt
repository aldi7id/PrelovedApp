package com.preloved.app.ui.fragment.homepage.account.password

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.response.SellerDeleteResponse
import com.preloved.app.data.network.model.response.UpdatePasswordResponse
import com.preloved.app.ui.fragment.homepage.sell.edit.EditProductContract
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class EditPasswordRepository(
    private val localDataSource: LocalDataSource,
    private val userDataSource: UserDataSource
): BaseRepositorylmpl(), EditPasswordContract.Repository {
    override suspend fun userSession(): Flow<DatastorePreferences> {
        return localDataSource.getUserSession()
    }

    override suspend fun updatePassword(
        token: String,
        oldPassword: String,
        newPassword: String,
        rePassword: String
    ): UpdatePasswordResponse {
        return userDataSource.putPassword(token, oldPassword, newPassword, rePassword)
    }
}