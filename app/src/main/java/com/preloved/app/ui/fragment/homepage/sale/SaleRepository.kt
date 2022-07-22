package com.preloved.app.ui.fragment.homepage.sale

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.HistoryResponseItem
import com.preloved.app.data.network.model.response.SellerDeleteResponse
import com.preloved.app.data.network.model.response.SellerOrderResponse
import com.preloved.app.data.network.model.response.SellerProductResponseItem
import com.preloved.app.data.network.model.response.UserResponse
import com.preloved.app.ui.fragment.homepage.account.AccountContract
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class SaleRepository(private val userDataSource: UserDataSource,
                     private val localDataSource: LocalDataSource
): BaseRepositorylmpl(), SaleContract.Repository {
    override suspend fun getSellerProduct(token: String): List<SellerProductResponseItem> {
        return userDataSource.getSellerProduct(token)
    }

    override suspend fun deleteSellerProduct(
        token: String,
        id: Int
    ): Response<SellerDeleteResponse> {
        return userDataSource.deleteSellerProduct(token,id)
    }

    override suspend fun getUserData(token: String): UserResponse {
        return userDataSource.getUserData(token)
    }

    override suspend fun userSession(): Flow<DatastorePreferences> {
        return localDataSource.getUserSession()
    }

    override suspend fun getSellerProductOrder(token: String): List<SellerOrderResponse> {
        return userDataSource.getSellerProductOrder(token)
    }

    override suspend fun getSellerProductSold(
        token: String,
        status: String
    ): List<SellerProductResponseItem> {
        return userDataSource.getSellerProductSold(token,status)
    }

    override suspend fun getHistory(token: String): List<HistoryResponseItem> {
        return userDataSource.getHistory(token)
    }
}