package com.preloved.app.ui.fragment.homepage.home.category.healthy

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.model.response.category.CategoryResponse

class CategoryHealthyRepository(
    private val categoryDataSource: CategoryDataSource
): BaseRepositorylmpl(), CategoryHealthyContract.Repository {
    override suspend fun getDataCategoryById(categoryId: Int): CategoryResponse {
        return categoryDataSource.getCategoryById(categoryId)
    }
}