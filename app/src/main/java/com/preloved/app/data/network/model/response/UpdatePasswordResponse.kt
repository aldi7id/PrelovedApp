package com.preloved.app.data.network.model.response


import com.google.gson.annotations.SerializedName

data class UpdatePasswordResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String
)