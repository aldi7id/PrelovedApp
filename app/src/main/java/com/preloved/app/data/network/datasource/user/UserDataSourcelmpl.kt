package com.preloved.app.data.network.datasource.user

import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.BuyerOrderResponse
import com.preloved.app.data.network.model.HistoryResponseItem
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.request.auth.UpdateProfileRequest
import com.preloved.app.data.network.model.response.*
import com.preloved.app.data.network.model.response.auth.LoginResponse
import com.preloved.app.data.network.model.response.auth.RegisterResponse
import com.preloved.app.data.network.services.PreLovedService
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
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
    override suspend fun getUserData(token: String): UserResponse {
        return preLovedService.getUserData(token)
    }

    override suspend fun getSellerProduct(token: String): List<SellerProductResponseItem> {
        return preLovedService.getSellerProduct(token)
    }

    override suspend fun deleteSellerProduct(
        token: String,
        id: Int
    ): Response<SellerDeleteResponse> {
        return preLovedService.deleteSellerProduct(token, id)
    }



    override suspend fun getSellerProductOrder(token: String): List<SellerOrderResponse> {
        return preLovedService.getSellerOrder(token)
    }

    override suspend fun getSellerProductOrderAccepted(token: String,status: String): List<SellerOrderResponse> {
        return preLovedService.getSellerOrderAccepted(token, status)
    }

    override suspend fun getNotification(token: String): List<NotificationResponse> {
        return preLovedService.getNotification(token)
    }

    override suspend fun getSellerProductId(
        token: String,
        id: Int
    ): SellerProductResponseItem {
        return preLovedService.getSellerProductId(token,id)
    }

    override suspend fun updateSellerProduct(
        token: String,
        id: Int,
        name: String,
        description: String,
        base_price: Int,
        category: List<Int>,
        location: String,
        image: File?
    ): PostProductResponse {
        val requestBodyBuilder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name",name)
            .addFormDataPart("description",description)
            .addFormDataPart("base_price", base_price.toString())
            .addFormDataPart("category_ids", category.toList().toString())
            .addFormDataPart("location", location)
        if (image != null ) {
            val requestFile = image.asRequestBody("image/jpg".toMediaType())
            requestBodyBuilder.addFormDataPart(
                "image", image.name, requestFile
            )
        }
        return preLovedService.updateSellerProduct(token,id,requestBodyBuilder.build())
    }

    override suspend fun getBuyerOrderById(token: String, id: Int): BuyerOrderResponse {
        return preLovedService.getBuyerOrderById(token, id)
    }

    override suspend fun getSellerOrderById(token: String, id: Int): SellerOrderResponse {
        return preLovedService.getSellerOrderById(token, id)
    }

    override suspend fun approveOrder(
        token: String,
        orderId: Int,
        requestApproveOrder: RequestApproveOrder
    ): ApproveOrderResponse {
        return preLovedService.approveOrder(token, orderId, requestApproveOrder)
    }

    override suspend fun getHistory(token: String): List<HistoryResponseItem> {
        return preLovedService.getHistory(token)
    }

    override suspend fun putPassword(
        token: String,
        current_password: String,
        new_password: String,
        confirm_password: String
    ): UpdatePasswordResponse {
        val requestBodyBuilder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("current_password", current_password)
            .addFormDataPart("new_password", new_password)
            .addFormDataPart("confirm_password", confirm_password)
        return preLovedService.putChangePassword(token, requestBodyBuilder.build())
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

    override suspend fun postProductData(
        token: String,
        name: String,
        description: String,
        base_price: Int,
        category: List<Int>,
        location: String,
        image: File?
    ): PostProductResponse {
        val requestBodyBuilder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name",name)
            .addFormDataPart("description",description)
            .addFormDataPart("base_price", base_price.toString())
            .addFormDataPart("category_ids", category.toList().toString())
            .addFormDataPart("location", location)
        if (image != null ) {
            val requestFile = image.asRequestBody("image/jpg".toMediaType())
            requestBodyBuilder.addFormDataPart(
                "image", image.name, requestFile
            )
        }
        return preLovedService.postProductData(token,requestBodyBuilder.build())
    }

    override suspend fun getCategoryData(): List<CategoryResponseItem> {
        return preLovedService.getCategoryData()
    }

//        {
}