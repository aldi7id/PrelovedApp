package com.preloved.app.ui.fragment.homepage.home.detail

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse

class DetailProductRepository(
    private val categoryDataSource: CategoryDataSource
): BaseRepositorylmpl(), DetailProductContract.Repository {
    override suspend fun getDetailProductById(productId: Int): CategoryDetailResponse {
        return categoryDataSource.getDetailProduct(productId)
    }
}