package com.preloved.app.ui.fragment.homepage.home.category.food

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.model.response.category.CategoryResponse

class CategoryFoodRepository(private val categoryDataSource: CategoryDataSource) :
    BaseRepositorylmpl(), CategoryFoodContract.Repository {
    override suspend fun getDataFoodCategory(foodId: Int): CategoryResponse {
        return categoryDataSource.getCategoryById(foodId)
    }
}