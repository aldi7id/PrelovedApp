package com.preloved.app.data.network.datasource

import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.*
import retrofit2.Call
import java.io.File

interface UserDataSource {
    suspend fun postLogin(loginRequest: LoginRequest): LoginResponse
    suspend fun postRegister(registerRequest: RegisterRequest): BaseAuthResponse<LoginResponse, String>
    suspend fun getSyncUser(): UserResponse

    suspend fun getProfileData(): UserResponse

    suspend fun getCategoryData(): List<CategoryResponseItem>

    suspend fun updateProfileData(
        email: String,
        nama: String,
        city: String,
        address: String,
        phone: String,
        profilePhoto: File? = null ) : UserResponse

    suspend fun postProductData(
        name: String,
        description: String,
        base_price : Int,
        category: Int,
        location: String,
        image : File? = null
    ) : PostProductResponse

}