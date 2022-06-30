package com.preloved.app.data.network.model.response


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String
)