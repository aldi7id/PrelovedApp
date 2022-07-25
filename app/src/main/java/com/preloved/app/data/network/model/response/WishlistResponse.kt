package com.preloved.app.data.network.model.response


import com.google.gson.annotations.SerializedName

data class WishlistResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("Product")
    val product: Product
)