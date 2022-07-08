package com.preloved.app.ui.fragment.homepage.sell.preview

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.response.PostProductResponse
import java.io.File

class PreviewProductRepository(private val userDataSource: UserDataSource,
                               private val localDataSource: LocalDataSource): BaseRepositorylmpl(), PreviewProductContract.Repository {
    override suspend fun postProductData(
        token: String,
        name: String,
        description: String,
        base_price: Int,
        category: List<Int>,
        location: String,
        image: File?
    ): PostProductResponse {
        val response = userDataSource.postProductData(token,name, description, base_price, category, location, image)
        return response
    }
}