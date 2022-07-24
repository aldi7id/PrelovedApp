package com.preloved.app.data.network.datasource

import com.preloved.app.data.network.model.BuyerOrderResponse
import com.preloved.app.data.network.model.HistoryResponseItem
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.request.auth.UpdateProfileRequest
import com.preloved.app.data.network.model.response.*
import com.preloved.app.data.network.model.response.auth.LoginResponse
import com.preloved.app.data.network.model.response.auth.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Path
import java.io.File

interface UserDataSource {
    suspend fun postLogin(loginRequest: LoginRequest): LoginResponse
    suspend fun postRegister(registerRequest: RegisterRequest): RegisterResponse

    suspend fun getProfileData(token: String): UserResponse

    suspend fun updateProfileData(
        token: String,
        email: String,
        nama: String,
        city: String,
        address: String,
        phone: String,
        profilePhoto: File? = null ) : UserResponse

    suspend fun postProductData(
        token: String,
        name: String,
        description: String,
        base_price : Int,
        category: List<Int>,
        location: String,
        image : File? = null
    ) : PostProductResponse
    suspend fun getCategoryData(): List<CategoryResponseItem>
    suspend fun getUserData(token: String): UserResponse
    suspend fun getSellerProduct(token: String): List<SellerProductResponseItem>
    suspend fun deleteSellerProduct(token: String, id: Int): Response<SellerDeleteResponse>
    suspend fun putPassword(
        token: String,
        current_password: String,
        new_password: String,
        confirm_password: String) : UpdatePasswordResponse
    suspend fun getSellerProductOrder(token: String) : List<SellerOrderResponse>
    suspend fun getSellerProductSold(token: String, status: String) : List<SellerProductResponseItem>
    suspend fun getSellerProductOrderAccepted(token: String, status: String) : List<SellerOrderResponse>
    suspend fun getNotification(token: String) : List<NotificationResponse>
    suspend fun getSellerProductId(token: String, id:Int): SellerProductResponseItem
    suspend fun updateSellerProduct(token: String,
                                    id: Int,
                                    name: String,
                                    description: String,
                                    base_price: Int,
                                    category: List<Int>,
                                    location: String,
                                    image : File? = null) : PostProductResponse
    suspend fun updateBuyerOrder(token: String, id: Int, bid_price : Int) : BuyerOrderEditResponse
    suspend fun getBuyerOrderById(token: String, id: Int) : BuyerOrderResponse
    suspend fun getBuyerOrder(token: String) : List<BuyerOrderResponse>
    suspend fun deleteBuyerOrderById(token: String, id: Int) : BuyerOrderResponse
    suspend fun getSellerOrderById(token: String, id: Int) : SellerOrderResponse
    suspend fun approveOrder(
        @Header("access_token") token: String,
        @Path("id") orderId: Int,
        @Body requestApproveOrder: RequestApproveOrder
    ): ApproveOrderResponse
    suspend fun getHistory(token: String) : List<HistoryResponseItem>
    suspend fun approveProduct(
        @Header("access_token") token: String,
        @Path("id") productId: Int,
        @Body requestApproveOrder: RequestApproveOrder
    ): ApproveProductResponse
   }