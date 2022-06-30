package com.preloved.app.data.network.model.request.category


import com.google.gson.annotations.SerializedName

class CategoryResponse : ArrayList<CategoryResponse.CategoryResponseItem>(){
    data class CategoryResponseItem(
        @SerializedName("base_price")
        val basePrice: Int,
        @SerializedName("Categories")
        val categories: List<Category>,
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image_name")
        val imageName: String,
        @SerializedName("image_url")
        val imageUrl: String,
        @SerializedName("location")
        val location: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("user_id")
        val userId: Int
    ) {
        data class Category(
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String
        )
    }
}