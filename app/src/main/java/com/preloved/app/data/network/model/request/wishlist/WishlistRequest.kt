package com.preloved.app.data.network.model.request.wishlist

import com.google.gson.annotations.SerializedName

data class WishlistRequest(
    @SerializedName("product_id")
    val productId: Int
)
