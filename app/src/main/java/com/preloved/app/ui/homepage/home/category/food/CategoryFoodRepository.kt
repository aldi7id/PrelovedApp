package com.preloved.app.ui.homepage.home.category.food

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.ui.homepage.home.category.all.CategoryAllContract

class CategoryFoodRepository(private val categoryDataSource: CategoryDataSource) : BaseRepositorylmpl(), CategoryFoodContract.Repository {

    override suspend fun getDataFoodCategory(foodId: Int): LoginRequest {
        return categoryDataSource.getCategoryFood(foodId)
    }
}