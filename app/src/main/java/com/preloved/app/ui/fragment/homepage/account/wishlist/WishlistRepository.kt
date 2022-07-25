package com.preloved.app.ui.fragment.homepage.account.wishlist

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.datasource.wishlist.WishlistDataSource
import com.preloved.app.data.network.model.response.whislist.GetWishlistResponse
import kotlinx.coroutines.flow.Flow

class WishlistRepository(
    private val localDataSource: LocalDataSource,
    private val wishlistDataSource: WishlistDataSource
): BaseRepositorylmpl(), WishlistContract.Repository {
    override suspend fun getAccessToken(): Flow<DatastorePreferences> {
        return localDataSource.getUserSession()
    }

    override suspend fun getWishlistProduct(accessToken: String): GetWishlistResponse {
        return wishlistDataSource.getWishlistProduct(accessToken)
    }
}