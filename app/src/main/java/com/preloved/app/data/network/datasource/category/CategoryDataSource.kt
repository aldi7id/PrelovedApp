package com.preloved.app.data.network.datasource.category

import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse

interface CategoryDataSource {

    suspend fun getCategoryAll(): CategoryResponse
    suspend fun getCategoryById(categoryById : Int) : CategoryResponse
    suspend fun getDetailProduct(detailById: Int): CategoryDetailResponse

}