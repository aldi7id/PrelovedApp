package com.preloved.app.ui.fragment.homepage.buyer.info

import com.preloved.app.base.arch.BaseRepositorylmpl
import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.model.response.ApproveOrderResponse
import com.preloved.app.data.network.model.response.ApproveProductResponse
import com.preloved.app.data.network.model.response.RequestApproveOrder
import com.preloved.app.data.network.model.response.SellerOrderResponse
import kotlinx.coroutines.flow.Flow

class BuyerInfoRepository(private val localDataSource: LocalDataSource,
                          private val userDataSource: UserDataSource
) : BaseRepositorylmpl(), BuyerInfoContract.Repository {
    override suspend fun userSession(): Flow<DatastorePreferences> {
        return localDataSource.getUserSession()
    }

    override suspend fun getSellerOrderById(token: String, id: Int): SellerOrderResponse {
        return userDataSource.getSellerOrderById(token,id)
    }

    override suspend fun approveOrder(
        token: String,
        id: Int,
        requestApproveOrder: RequestApproveOrder
    ): ApproveOrderResponse {
        return userDataSource.approveOrder(token,id,requestApproveOrder)
    }

    override suspend fun approveProduct(
        token: String,
        id: Int,
        requestApproveOrder: RequestApproveOrder
    ): ApproveProductResponse {
        return userDataSource.approveProduct(token,id,requestApproveOrder)
    }

}