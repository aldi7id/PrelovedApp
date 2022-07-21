package com.preloved.app.ui.fragment.homepage.home.bid

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.model.request.bid.BidRequest
import com.preloved.app.data.network.model.response.bid.get.GetBidResponse
import com.preloved.app.data.network.model.response.bid.post.PostBidResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class PopUpBidRepository(
    private val localDataSource: LocalDataSource,
    private val categoryDataSource: CategoryDataSource
): BaseRepositorylmpl(), PopUpBidContract.Repository {
    override suspend fun getDetailProductById(productId: Int): CategoryDetailResponse {
        return categoryDataSource.getDetailProduct(productId)
    }

    override suspend fun getBidProductOrder(accessToken: String): GetBidResponse {
        return categoryDataSource.getDataProductOrder(accessToken)
    }

    override suspend fun postBidProductOrderById(
        accessToken: String,
        bidRequest: BidRequest
    ): Response<PostBidResponse> {
        return categoryDataSource.postDataProductOrderById(accessToken, bidRequest)
    }

    override suspend fun getAccessToken(): Flow<DatastorePreferences> {
        return localDataSource.getUserSession()
    }
}