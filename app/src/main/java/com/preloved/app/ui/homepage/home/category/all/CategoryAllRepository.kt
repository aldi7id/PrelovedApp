package com.preloved.app.ui.homepage.home.category.all

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.response.LoginResponse

class CategoryAllRepository(private val categoryDataSource: CategoryDataSource) : BaseRepositorylmpl(), CategoryAllContract.Repository {
    override suspend fun getDataAllCategory(): LoginRequest {
      return categoryDataSource.getCategoryAll()
    }
}