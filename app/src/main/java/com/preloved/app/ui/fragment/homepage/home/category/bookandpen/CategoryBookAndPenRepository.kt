package com.preloved.app.ui.fragment.homepage.home.category.bookandpen

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.model.response.category.CategoryResponse

class CategoryBookAndPenRepository(
    private val categoryDataSource: CategoryDataSource
): BaseRepositorylmpl(), CategoryBookAndPenContract.Repository {
    override suspend fun getDataCategoryById(categoryId: Int): CategoryResponse {
        return categoryDataSource.getCategoryById(categoryId)
    }
}