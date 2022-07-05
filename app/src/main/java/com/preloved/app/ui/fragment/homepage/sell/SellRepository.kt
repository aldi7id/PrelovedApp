package com.preloved.app.ui.fragment.homepage.sell

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.data.network.model.response.PostProductResponse
import com.preloved.app.data.network.model.response.UserResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

class SellRepository(private val userDataSource: UserDataSource,
                     private val localDataSource: LocalDataSource
): BaseRepositorylmpl(), SellContract.Repository {
    override suspend fun getCategoryData(): List<CategoryResponseItem> {
        val response = userDataSource.getCategoryData()
        return response

    }

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

    override suspend fun getUserData(token: String): UserResponse {
        return userDataSource.getUserData(token)
    }

    override suspend fun userSession(): Flow<DatastorePreferences> {
        return localDataSource.getUserSession()
    }

    override suspend fun deleteToken() {
        return localDataSource.deleteUserSession()
    }
}