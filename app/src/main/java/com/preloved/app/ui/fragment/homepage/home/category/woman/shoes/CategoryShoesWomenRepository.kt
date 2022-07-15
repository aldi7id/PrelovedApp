package com.preloved.app.ui.fragment.homepage.home.category.woman.shoes

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.model.response.category.CategoryResponse

class CategoryShoesWomenRepository(
    private val categoryDataSource: CategoryDataSource
): BaseRepositorylmpl(), CategoryShoesWomenContract.Repository {
    override suspend fun getDataCategoryById(categoryId: Int): CategoryResponse {
        return categoryDataSource.getCategoryById(categoryId)
    }
}