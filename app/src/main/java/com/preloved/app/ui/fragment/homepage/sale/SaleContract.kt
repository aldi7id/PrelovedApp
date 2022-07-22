package com.preloved.app.ui.fragment.homepage.sale

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.HistoryResponseItem
import com.preloved.app.data.network.model.response.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SaleContract {
    interface View {
        fun setOnClickListeners()
    }
    interface ViewModel {
        fun userSession()
        fun userSessionResult(): LiveData<DatastorePreferences>
        fun getUserDataResult(): LiveData<Resource<UserResponse>>
        fun getUserData(token: String)
        fun getSellerProduct(token: String)
        fun getSellerProductResult(): LiveData<Resource<List<SellerProductResponseItem>>>
        fun getSellerProductOrder(token: String)
        fun getSellerProductOrderResult(): LiveData<Resource<List<SellerOrderResponse>>>
        fun getSellerProductOrderAccepted(token: String, status: String)
        fun getSellerProductOrderAcceptedResult(): LiveData<Resource<List<SellerOrderResponse>>>
        fun getHistory(token: String)
        fun getHistoryResult(): MutableLiveData<Resource<List<HistoryResponseItem>>>
    }

    interface Repository {
        suspend fun getSellerProduct(token: String) : List<SellerProductResponseItem>
        suspend fun deleteSellerProduct(token: String, id : Int) : Response<SellerDeleteResponse>
        suspend fun getUserData(token: String): UserResponse
        suspend fun userSession(): Flow<DatastorePreferences>
        suspend fun getSellerProductOrder(token: String) : List<SellerOrderResponse>
        suspend fun getSellerProductOrderAccepted(token: String, status: String): List<SellerOrderResponse>
        suspend fun getHistory(token: String) : List<HistoryResponseItem>
    }
}