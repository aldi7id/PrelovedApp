package com.preloved.app.data.network.model.response.whislist


import com.google.gson.annotations.SerializedName

data class DeleteWishlistResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String
)