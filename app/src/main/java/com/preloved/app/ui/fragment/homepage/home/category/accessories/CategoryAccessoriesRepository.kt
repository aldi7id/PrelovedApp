package com.preloved.app.ui.fragment.homepage.home.category.accessories

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.model.response.category.CategoryResponse

class CategoryAccessoriesRepository(
    private val categoryDataSource: CategoryDataSource
): BaseRepositorylmpl(), CategoryAccessoriesContract.Repository {
    override suspend fun getDataCategoryById(categoryId: Int): CategoryResponse {
        return categoryDataSource.getCategoryById(categoryId)
    }
}