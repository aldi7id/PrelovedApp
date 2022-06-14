package com.preloved.app.data.network.model.response

import com.google.gson.annotations.SerializedName

data class BaseAuthResponse<D,E>(
    @SerializedName("success")
    val isSuccess : Boolean,
    @SerializedName("data")
    val data : D,
    @SerializedName("errors")
    val errorMsg : E
)