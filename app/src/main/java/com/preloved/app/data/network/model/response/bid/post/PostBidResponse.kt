package com.preloved.app.data.network.model.response.bid.post


import com.google.gson.annotations.SerializedName

data class PostBidResponse(
    @SerializedName("base_price")
    val basePrice: String,
    @SerializedName("buyer_id")
    val buyerId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_product")
    val imageProduct: Any,
    @SerializedName("price")
    val price: Int,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("transaction_date")
    val transactionDate: Any,
    @SerializedName("updatedAt")
    val updatedAt: String
)