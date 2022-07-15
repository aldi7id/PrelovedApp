package com.preloved.app.ui.fragment.homepage.home.category.homesupplies

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.model.response.category.CategoryResponse

class CategoryHomeSuppliesRepository(
    private val categoryDataSource: CategoryDataSource
): BaseRepositorylmpl(), CategoryHomeSuppliesContract.Repository {
    override suspend fun getDataCategoryById(categoryId: Int): CategoryResponse {
        return categoryDataSource.getCategoryById(categoryId)
    }
}