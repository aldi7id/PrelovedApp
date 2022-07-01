package com.preloved.app.ui.profile.edit

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.response.LoginResponse
import com.preloved.app.data.network.model.response.UserResponse
import java.io.File

class EditProfileRepository(
    private val userDataSource: UserDataSource
):BaseRepositorylmpl(), EditProfileContract.Repository {
    override fun clearSession() {
        //
    }

    override suspend fun saveCacheProfileData(response: UserResponse) {
//        if (response.isSuccess){
//            val modifiedUserData = local
//        }
    }

    override suspend fun getProfileData(): UserResponse {
        val response = userDataSource.getProfileData()
        saveCacheProfileData(response)
        return response
    }

    override suspend fun updateProfileData(email: String,
                                           nama: String,
                                           city: String,
                                           address: String,
                                           phone: String,
                                           profilePhoto: File?): UserResponse {
        val response = userDataSource.updateProfileData(email,nama,city,address,phone,profilePhoto)
        saveCacheProfileData(response)
        return response
    }
}