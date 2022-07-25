package com.preloved.app.ui.fragment.homepage.account.myorder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.BuyerOrderResponse
import com.preloved.app.data.network.model.HistoryResponseItem
import com.preloved.app.data.network.model.response.SellerDeleteResponse
import com.preloved.app.data.network.model.response.SellerOrderResponse
import com.preloved.app.data.network.model.response.SellerProductResponseItem
import com.preloved.app.data.network.model.response.UserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MyOrderContract {
    interface View {
        fun setOnClickListeners()
    }
    interface ViewModel {
        fun userSession()
        fun userSessionResult(): LiveData<DatastorePreferences>
        fun getBuyerOrder(token: String)
        fun getBuyerOrderResult(): MutableLiveData<Resource<List<BuyerOrderResponse>>>
        fun deleteBuyerOrder(token: String, id: Int)
        fun deleteBuyerOrderResult(): MutableLiveData<Resource<BuyerOrderResponse>>

    }

    interface Repository {
        suspend fun getBuyerOrder(token: String) : List<BuyerOrderResponse>
        suspend fun deleteBuyerOrder(token: String, id : Int) : BuyerOrderResponse
        suspend fun userSession(): Flow<DatastorePreferences>
    }
}