package com.preloved.app.ui.fragment.homepage.home.category.computer

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.model.response.category.CategoryResponse

class CategoryComputerRepository(
    private val categoryDataSource: CategoryDataSource
): BaseRepositorylmpl(), CategoryComputerContract.Repository {
    override suspend fun getDataComputerById(categoryId: Int): CategoryResponse {
        return categoryDataSource.getCategoryById(categoryId)
    }
}