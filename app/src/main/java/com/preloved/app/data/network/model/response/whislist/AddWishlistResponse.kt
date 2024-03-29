package com.preloved.app.data.network.model.response.whislist


import com.google.gson.annotations.SerializedName

data class AddWishlistResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("product")
    val product: Product
) {
    data class Product(
        @SerializedName("base_price")
        val basePrice: Int,
        @SerializedName("createdAt")
        val createdAt: String,
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
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("user_id")
        val userId: Int
    )
}