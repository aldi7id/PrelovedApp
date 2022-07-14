package com.preloved.app.ui.fragment.homepage.home.category.man.shoes

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.model.response.category.CategoryResponse

class CategoryShoesMenRepository(
    private val categoryDataSource: CategoryDataSource
): BaseRepositorylmpl(), CategoryShoesMenContract.Repository {
    override suspend fun getDataCategoryById(categoryId: Int): CategoryResponse {
        return categoryDataSource.getCategoryById(categoryId)
    }
}