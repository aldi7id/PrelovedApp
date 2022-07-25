package com.preloved.app.ui.fragment.homepage.buyer.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.File

interface BuyerInfoContract {
    interface View {
        fun setOnClickListeners()
        fun setDataToView(data: SellerOrderResponse)
    }

    interface ViewModel {
        fun userSession()
        fun userSessionResult(): LiveData<DatastorePreferences>
        fun getSellerOrderById(token: String, id: Int)
        fun getSellerOrderByIdResult(): MutableLiveData<Resource<SellerOrderResponse>>
        fun statusOrder(token: String, orderId: Int, requestApproveOrder: RequestApproveOrder)
        fun statusOrderResult() : MutableLiveData<Resource<ApproveOrderResponse>>
        fun statusProcutAccepted(token: String, productId: Int, requestApproveOrder: RequestApproveOrder)
        fun statusProcutAcceptedResult() : MutableLiveData<Resource<ApproveOrderResponse>>
    }

    interface Repository {
        suspend fun userSession(): Flow<DatastorePreferences>
        suspend fun getSellerOrderById(token : String, id: Int) : SellerOrderResponse
        suspend fun approveOrder(token: String, id: Int, requestApproveOrder: RequestApproveOrder) : ApproveOrderResponse
        suspend fun approveProductAccepted(token: String, id: Int, requestApproveOrder: RequestApproveOrder) : ApproveOrderResponse

    }
}