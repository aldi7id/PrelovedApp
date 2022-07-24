package com.preloved.app.ui.fragment.homepage.home.bid.edit

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.model.request.bid.BidRequest
import com.preloved.app.data.network.model.response.BuyerOrderEditResponse
import com.preloved.app.data.network.model.response.bid.get.GetBidResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import com.preloved.app.ui.fragment.homepage.home.bid.PopUpBidContract
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class PopUpBidEditRepository(private val localDataSource: LocalDataSource,
                             private val categoryDataSource: CategoryDataSource,
                             private val userDataSource: UserDataSource
): BaseRepositorylmpl(), PopUpBidEditContract.Repository {
    override suspend fun getDetailProductById(productId: Int): CategoryDetailResponse {
        return categoryDataSource.getDetailProduct(productId)
    }

    override suspend fun getBidProductOrder(accessToken: String): GetBidResponse {
        return categoryDataSource.getDataProductOrder(accessToken)
    }

    override suspend fun postBidProductOrderById(
        token: String,
        id: Int,
        bid_price: Int
    ): BuyerOrderEditResponse {
        return userDataSource.updateBuyerOrder(token, id, bid_price)
    }

    override suspend fun getAccessToken(): Flow<DatastorePreferences> {
        return localDataSource.getUserSession()
    }
}