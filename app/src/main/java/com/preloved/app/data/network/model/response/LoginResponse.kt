package com.preloved.app.data.network.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)