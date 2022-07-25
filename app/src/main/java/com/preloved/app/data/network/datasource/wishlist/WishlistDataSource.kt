package com.preloved.app.data.network.datasource.wishlist

import com.preloved.app.data.network.model.request.wishlist.WishlistRequest
import com.preloved.app.data.network.model.response.whislist.AddWishlistResponse
import com.preloved.app.data.network.model.response.whislist.DeleteWishlistResponse
import com.preloved.app.data.network.model.response.whislist.GetWishlistByIdResponse
import com.preloved.app.data.network.model.response.whislist.GetWishlistResponse

interface WishlistDataSource {
    suspend fun addWishlistProduct(token: String, wishlistRequest: WishlistRequest): AddWishlistResponse
    suspend fun getWishlistProduct(token: String): GetWishlistResponse
    suspend fun getWishlistProductById(token: String, productId: Int): GetWishlistByIdResponse
    suspend fun deleteWishlistProductById(token: String, productId: Int): DeleteWishlistResponse
}