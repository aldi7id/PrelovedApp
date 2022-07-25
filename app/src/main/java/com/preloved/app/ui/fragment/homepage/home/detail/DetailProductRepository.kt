package com.preloved.app.ui.fragment.homepage.home.detail

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.datasource.wishlist.WishlistDataSource
import com.preloved.app.data.network.model.BuyerOrderResponse
import com.preloved.app.data.network.model.request.wishlist.WishlistRequest
import com.preloved.app.data.network.model.response.bid.get.GetBidResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import com.preloved.app.data.network.model.response.whislist.AddWishlistResponse
import com.preloved.app.data.network.model.response.whislist.DeleteWishlistResponse
import com.preloved.app.data.network.model.response.whislist.GetWishlistResponse
import kotlinx.coroutines.flow.Flow

class DetailProductRepository(
    private val localDataSource: LocalDataSource,
    private val categoryDataSource: CategoryDataSource,
    private val userDataSource: UserDataSource,
    private val wishlistDataSource: WishlistDataSource
): BaseRepositorylmpl(), DetailProductContract.Repository {
    override suspend fun getTokenAccess(): Flow<DatastorePreferences> {
        return localDataSource.getUserSession()
    }

    override suspend fun getDetailProductById(productId: Int): CategoryDetailResponse {
        return categoryDataSource.getDetailProduct(productId)
    }

    override suspend fun getBuyerOrder(tokenAccess: String): GetBidResponse {
        return categoryDataSource.getDataProductOrder(tokenAccess)
    }

    override suspend fun deleteBuyerOrder(token: String, id: Int): BuyerOrderResponse {
        return userDataSource.deleteBuyerOrderById(token,id)
    }

    override suspend fun postWishlistProduct(
        tokenAccess: String,
        wishlistRequest: WishlistRequest
    ): AddWishlistResponse {
        return wishlistDataSource.addWishlistProduct(tokenAccess, wishlistRequest)
    }

    override suspend fun getWishlistProduct(tokenAccess: String): GetWishlistResponse {
        return wishlistDataSource.getWishlistProduct(tokenAccess)
    }

    override suspend fun deleteWishlistProductById(
        tokenAccess: String,
        productId: Int
    ): DeleteWishlistResponse {
        return wishlistDataSource.deleteWishlistProductById(tokenAccess, productId)
    }
}