package com.preloved.app.ui.fragment.homepage.account.myorder

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.BuyerOrderResponse
import com.preloved.app.ui.fragment.homepage.sale.SaleContract
import kotlinx.coroutines.flow.Flow

class MyOrderRepository(private val userDataSource: UserDataSource,
                        private val localDataSource: LocalDataSource
): BaseRepositorylmpl(), MyOrderContract.Repository {
    override suspend fun getBuyerOrder(token: String): List<BuyerOrderResponse> {
        return userDataSource.getBuyerOrder(token)
    }

    override suspend fun deleteBuyerOrder(token: String, id: Int): BuyerOrderResponse {
        return userDataSource.deleteBuyerOrderById(token, id)
    }

    override suspend fun userSession(): Flow<DatastorePreferences> {
        return localDataSource.getUserSession()
    }
}