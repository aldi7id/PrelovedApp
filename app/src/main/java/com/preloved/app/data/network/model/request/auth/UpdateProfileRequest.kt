package com.preloved.app.data.network.model.request.auth

import com.google.gson.annotations.SerializedName
import java.io.File

data class UpdateProfileRequest(
    @SerializedName("address")
    val address: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("image_url")
    val image: File,
    @SerializedName("phone_number")
    val phone: Long,
)