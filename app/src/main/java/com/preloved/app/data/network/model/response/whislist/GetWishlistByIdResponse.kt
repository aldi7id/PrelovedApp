package com.preloved.app.data.network.model.response.whislist


import com.google.gson.annotations.SerializedName

data class GetWishlistByIdResponse(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("Product")
    val product: Product,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
) {
    data class Product(
        @SerializedName("base_price")
        val basePrice: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image_name")
        val imageName: String,
        @SerializedName("image_url")
        val imageUrl: String,
        @SerializedName("location")
        val location: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("user_id")
        val userId: Int
    )
}