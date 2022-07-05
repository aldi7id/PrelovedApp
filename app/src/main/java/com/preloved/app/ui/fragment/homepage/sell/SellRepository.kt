package com.preloved.app.ui.fragment.homepage.sell

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.data.network.model.response.PostProductResponse
import java.io.File

class SellRepository(private val userDataSource: UserDataSource
): BaseRepositorylmpl(), SellContract.Repository {
    override suspend fun getCategoryData(): List<CategoryResponseItem> {
        val response = userDataSource.getCategoryData()
        return response

    }

    override suspend fun postProductData(
        name: String,
        description: String,
        base_price: Int,
        category: Int,
        location: String,
        image: File?
    ): PostProductResponse {
        val response = userDataSource.postProductData(name, description, base_price, category, location, image)
        return response
    }

    override suspend fun category(): List<String> {
        return emptyList()
    }
}