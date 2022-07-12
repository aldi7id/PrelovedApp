package com.preloved.app.ui.fragment.homepage.sell.edit

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.data.network.model.response.PostProductResponse
import com.preloved.app.data.network.model.response.SellerDeleteResponse
import com.preloved.app.data.network.model.response.SellerProductResponseItem
import com.preloved.app.ui.profile.edit.EditProfileContract
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.File

class EditProductRepository(
    private val localDataSource: LocalDataSource,
    private val userDataSource: UserDataSource
): BaseRepositorylmpl(), EditProductContract.Repository {
    override suspend fun userSession(): Flow<DatastorePreferences> {
        return localDataSource.getUserSession()
    }

    override suspend fun getProductData(token: String, id: Int): SellerProductResponseItem {
        return userDataSource.getSellerProductId(token, id)
    }


    override suspend fun updateProductData(
        token: String,
        id: Int,
        name: String,
        description: String,
        base_price: Int,
        category: List<Int>,
        location: String,
        image: File?
    ): PostProductResponse {
        return userDataSource.updateSellerProduct(token, id, name, description, base_price, category, location, image)
    }

    override suspend fun getCategoryData(): List<CategoryResponseItem> {
        return userDataSource.getCategoryData()
    }

    override suspend fun deleteSellerProduct(
        token: String,
        id: Int
    ): Response<SellerDeleteResponse> {
        return userDataSource.deleteSellerProduct(token, id)
    }
}