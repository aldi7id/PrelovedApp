package com.preloved.app.data.network.model.response


import com.google.gson.annotations.SerializedName

data class BuyerWishlistResponseItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Product")
    val product: Product,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("user_id")
    val userId: Int
)