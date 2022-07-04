package com.preloved.app.data.network.datasource.user

import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.request.auth.UpdateProfileRequest
import com.preloved.app.data.network.model.response.LoginResponse
import com.preloved.app.data.network.model.response.RegisterResponse
import com.preloved.app.data.network.model.response.UpdateProfileResponse
import com.preloved.app.data.network.model.response.UserResponse
import com.preloved.app.data.network.services.PreLovedService
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserDataSourcelmpl(private val preLovedService: PreLovedService): UserDataSource {
    override suspend fun postLogin(loginRequest: LoginRequest): LoginResponse {
        return preLovedService.postLogin(loginRequest)
    }
    override suspend fun postRegister(registerRequest: RegisterRequest): RegisterResponse {
        return preLovedService.postRegisterUser(registerRequest)
    }

    override suspend fun getProfileData(token: String): UserResponse {
        return preLovedService.getUserData(token)
    }

    override suspend fun updateProfileData(token: String,
                                           email: String,
                                           nama: String,
                                           city: String,
                                           address: String,
                                           phone: String,
                                           profilePhoto: File? ): UserResponse {
        val requestBodyBuilder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("email", email)
            .addFormDataPart("full_name", nama)
            .addFormDataPart("phone_number", phone)
            .addFormDataPart("address", address)
            .addFormDataPart("city", city)
        if (profilePhoto != null){
            val requestFile = profilePhoto.asRequestBody("image/jpg".toMediaType())
            requestBodyBuilder.addFormDataPart(
                "image", profilePhoto.name, requestFile
                )
        }
        return preLovedService.putUserData(token,requestBodyBuilder.build())
    }

    override suspend fun getUserData(token: String): UserResponse {
        return preLovedService.getUserData(token)
    }

//        {
}