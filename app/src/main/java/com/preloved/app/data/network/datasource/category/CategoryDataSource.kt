package com.preloved.app.data.network.datasource.category

import com.preloved.app.data.network.model.request.bid.BidRequest
import com.preloved.app.data.network.model.response.bid.get.GetBidResponse
import com.preloved.app.data.network.model.response.bid.post.PostBidResponse
import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import retrofit2.Response

interface CategoryDataSource {
    suspend fun getCategoryAll(): CategoryResponse
    suspend fun getCategoryById(categoryById : Int) : CategoryResponse
    suspend fun getSearchProduct(search: String): CategoryResponse
    suspend fun getDetailProduct(detailById: Int): CategoryDetailResponse
    suspend fun getDataProductOrder(accessToken: String): GetBidResponse
    suspend fun postDataProductOrderById(accessToken: String, bidRequest: BidRequest): Response<PostBidResponse>
}