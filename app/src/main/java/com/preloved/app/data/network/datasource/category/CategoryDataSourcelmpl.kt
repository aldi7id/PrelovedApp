package com.preloved.app.data.network.datasource.category

import com.preloved.app.data.network.model.request.bid.BidRequest
import com.preloved.app.data.network.model.response.bid.get.GetBidResponse
import com.preloved.app.data.network.model.response.bid.post.PostBidResponse
import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import com.preloved.app.data.network.service.CategoryService
import retrofit2.Response

class CategoryDataSourcelmpl(private val categoryService: CategoryService) : CategoryDataSource {
    override suspend fun getCategoryAll(): CategoryResponse {
        return categoryService.getCategoryAll()
    }

    override suspend fun getCategoryById(categoryById: Int): CategoryResponse {
        return categoryService.getFilterCategoryByID(categoryById)
    }

    override suspend fun getSearchProduct(search: String): CategoryResponse {
        return categoryService.getSearchProduct(search)
    }

    override suspend fun getDetailProduct(detailById: Int): CategoryDetailResponse {
        return categoryService.getDetailCategoryById(detailById)
    }

    override suspend fun getDataProductOrder(accessToken: String): GetBidResponse {
        return categoryService.getBidProductOrder(accessToken)
    }

    override suspend fun postDataProductOrderById(
        accessToken: String,
        bidRequest: BidRequest
    ): Response<PostBidResponse> {
        return categoryService.postBidProductOrder(accessToken, bidRequest)
    }
}