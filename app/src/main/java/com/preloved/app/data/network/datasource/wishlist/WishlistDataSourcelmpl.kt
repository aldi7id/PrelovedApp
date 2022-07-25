package com.preloved.app.data.network.datasource.wishlist

import com.preloved.app.data.network.model.request.wishlist.WishlistRequest
import com.preloved.app.data.network.model.response.whislist.AddWishlistResponse
import com.preloved.app.data.network.model.response.whislist.DeleteWishlistResponse
import com.preloved.app.data.network.model.response.whislist.GetWishlistByIdResponse
import com.preloved.app.data.network.model.response.whislist.GetWishlistResponse
import com.preloved.app.data.network.service.WishlistService

class WishlistDataSourcelmpl(private val wishlistService: WishlistService): WishlistDataSource {
    override suspend fun addWishlistProduct(token: String, wishlistRequest: WishlistRequest): AddWishlistResponse {
        return wishlistService.addWishlistProduct(token, wishlistRequest)
    }

    override suspend fun getWishlistProduct(token: String): GetWishlistResponse {
        return wishlistService.getWishlistProduct(token)
    }

    override suspend fun getWishlistProductById(
        token: String,
        productId: Int
    ): GetWishlistByIdResponse {
        return wishlistService.getWishlistProductById(token, productId)
    }

    override suspend fun deleteWishlistProductById(
        token: String,
        productId: Int
    ): DeleteWishlistResponse {
        return wishlistService.deleteWishlistProductById(token, productId)
    }
}