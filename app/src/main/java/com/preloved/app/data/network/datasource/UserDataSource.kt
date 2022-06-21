package com.preloved.app.data.network.datasource

import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.BaseAuthResponse
import com.preloved.app.data.network.model.response.LoginResponse
import com.preloved.app.data.network.model.response.User
import com.preloved.app.data.network.model.response.UserResponse
import java.io.File

interface UserDataSource {
    suspend fun postLogin(loginRequest: LoginRequest): LoginResponse
    suspend fun postRegister(registerRequest: RegisterRequest): BaseAuthResponse<LoginResponse, String>
    suspend fun getSyncUser(): UserResponse

    suspend fun getProfileData(): UserResponse

    suspend fun updateProfileData(
        email: String,
        nama: String,
        city: String,
        address: String,
        phone: String,
        profilePhoto: File? = null ) : UserResponse


}