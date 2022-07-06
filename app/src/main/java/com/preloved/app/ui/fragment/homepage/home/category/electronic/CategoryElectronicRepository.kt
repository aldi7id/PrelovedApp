package com.preloved.app.ui.fragment.homepage.home.category.electronic

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.model.response.category.CategoryResponse

class CategoryElectronicRepository(
    private val categoryDataSource: CategoryDataSource
): BaseRepositorylmpl(), CategoryElectronicContract.Repository {
    override suspend fun getDataElectronicById(electronicId: Int): CategoryResponse {
        return categoryDataSource.getCategoryById(electronicId)
    }
}