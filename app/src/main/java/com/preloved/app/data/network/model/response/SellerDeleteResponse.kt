package com.preloved.app.data.network.model.response

import com.google.gson.annotations.SerializedName

data class SellerDeleteResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("msg")
    val msg: String
)