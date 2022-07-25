package com.preloved.app.data.network.model.request.bid


import com.google.gson.annotations.SerializedName

data class BidRequest(
    @SerializedName("bid_price")
    val bidPrice: Int,
    @SerializedName("product_id")
    val productId: Int
)