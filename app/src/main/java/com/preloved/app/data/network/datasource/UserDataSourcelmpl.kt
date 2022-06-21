package com.preloved.app.data.network.datasource

import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.BaseAuthResponse
import com.preloved.app.data.network.model.response.LoginResponse
import com.preloved.app.data.network.model.response.UserResponse
import com.preloved.app.data.network.services.PreLovedService
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UserDataSourcelmpl(private val preLovedService: PreLovedService): UserDataSource {
    override suspend fun postLogin(loginRequest: LoginRequest): LoginResponse {
        return preLovedService.postLogin(loginRequest)
    }

    override suspend fun postRegister(registerRequest: RegisterRequest): BaseAuthResponse<LoginResponse, String> {
        return preLovedService.postRegisterUser(registerRequest)
    }

    override suspend fun getSyncUser(): UserResponse {
        return preLovedService.getSyncUser()
    }

    override suspend fun getProfileData(): UserResponse {
        return preLovedService.getUserData()
    }

    override suspend fun updateProfileData(userResponse: UserResponse): UserResponse {
        return preLovedService.putUserData(userResponse)
    }
//        {
//       val requestBodyBuilder = MultipartBody.Builder()
//           .setType(MultipartBody.FORM)
//           .addFormDataPart("email", email)
//           .addFormDataPart("full_name", full_name)
//           .addFormDataPart("password", password)
//           .addFormDataPart("phone_number", phone_number)
//           .addFormDataPart("address", address)
//           .addFormDataPart("city", city)
//        if (image_url != null){
//            requestBodyBuilder.addFormDataPart(
//                "image_url", image_url.name , RequestBody.create(
//                    "image/*".toMediaTypeOrNull(),
//                    image_url
//                )
//            )
//        }
//        return preLovedService.putUserData(requestBodyBuilder.build())
//    }
}