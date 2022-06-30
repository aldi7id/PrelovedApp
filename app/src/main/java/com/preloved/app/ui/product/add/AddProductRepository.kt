package com.preloved.app.ui.product.add

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.data.network.model.response.PostProductResponse
import java.io.File

class AddProductRepository(private val userDataSource: UserDataSource
): BaseRepositorylmpl(), AddProductContract.Repository {
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
}