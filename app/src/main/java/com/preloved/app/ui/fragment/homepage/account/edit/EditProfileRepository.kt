package com.preloved.app.ui.fragment.homepage.account.edit

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.response.UserResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

class EditProfileRepository(
    private val localDataSource: LocalDataSource,
    private val userDataSource: UserDataSource
):BaseRepositorylmpl(), EditProfileContract.Repository {
    override suspend fun userSession(): Flow<DatastorePreferences> {
        return localDataSource.getUserSession()
    }

    override fun clearSession() {
        //
    }

    override suspend fun saveCacheProfileData(response: UserResponse) {
//        if (response.isSuccess){
//            val modifiedUserData = local
//        }
    }

    override suspend fun getProfileData(token: String): UserResponse {
        val response = userDataSource.getProfileData(token)
        saveCacheProfileData(response)
        return response
    }

    override suspend fun updateProfileData(token: String,
                                           email: String,
                                           nama: String,
                                           city: String,
                                           address: String,
                                           phone: String,
                                           profilePhoto: File?): UserResponse {
        val response = userDataSource.updateProfileData(token, email, nama, city, address, phone, profilePhoto)
        saveCacheProfileData(response)
        return response
    }
}