package com.preloved.app.data.network.datasource.category

import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import com.preloved.app.data.network.service.CategoryService

class CategoryDataSourcelmpl(private val categoryService: CategoryService) : CategoryDataSource {
    override suspend fun getCategoryAll(): CategoryResponse {
        return categoryService.getCategoryAll()
    }

    override suspend fun getCategoryById(categoryById: Int): CategoryResponse {
        return categoryService.getFilterCategoryByID(categoryById)
    }

    override suspend fun getDetailProduct(detailById: Int): CategoryDetailResponse {
        return categoryService.getDetailCategoryById(detailById)
    }
}