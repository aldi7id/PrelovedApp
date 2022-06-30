package com.preloved.app.ui.fragment.homepage.home.category.all

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.model.request.category.CategoryResponse

class CategoryAllRepository(private val categoryDataSource: CategoryDataSource) :
    BaseRepositorylmpl(), CategoryAllContract.Repository {
    override suspend fun getDataAllCategory(): CategoryResponse {
        return categoryDataSource.getCategoryAll()
    }
}