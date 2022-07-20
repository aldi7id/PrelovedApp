package com.preloved.app.data.network.model.response

import com.google.gson.annotations.SerializedName

data class RequestApproveOrder(
    @SerializedName("status")
    val status: String
)