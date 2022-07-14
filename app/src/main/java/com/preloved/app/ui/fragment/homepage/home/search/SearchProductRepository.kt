package com.preloved.app.ui.fragment.homepage.home.search

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.data.network.service.CategoryService

class SearchProductRepository(
    private val categoryService: CategoryService
): BaseRepositorylmpl(), SearchProductContract.Repository {
    override suspend fun getDataSearchProduct(search: String): CategoryResponse {
        return categoryService.getSearchProduct(search)
    }
}