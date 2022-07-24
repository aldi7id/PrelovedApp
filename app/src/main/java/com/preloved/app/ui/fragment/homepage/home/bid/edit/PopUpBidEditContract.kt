package com.preloved.app.ui.fragment.homepage.home.bid.edit

import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.request.bid.BidRequest
import com.preloved.app.data.network.model.response.BuyerOrderEditResponse
import com.preloved.app.data.network.model.response.bid.get.GetBidResponse
import com.preloved.app.data.network.model.response.bid.post.PostBidResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface PopUpBidEditContract {
    interface ViewModel: BaseContract.BaseViewModel {
        fun getBidProductOrderResult(): MutableLiveData<Resource<GetBidResponse>>
        fun getDetailProductResult(): MutableLiveData<Resource<CategoryDetailResponse>>
        fun postBidProductOrderByIdResult(): MutableLiveData<Resource<BuyerOrderEditResponse>>
        fun getDetailProductById(productId: Int)
        fun postBidProductOrderById(token: String, id: Int, bid_price : Int)
        fun getTokenAccess()
        fun getTokenAccessResult(): MutableLiveData<DatastorePreferences>
    }
    interface Repository: BaseContract.BaseRepository {
        suspend fun getDetailProductById(productId: Int): CategoryDetailResponse
        suspend fun getBidProductOrder(accessToken: String): GetBidResponse
        suspend fun postBidProductOrderById(token: String, id: Int, bid_price : Int): BuyerOrderEditResponse
        suspend fun getAccessToken(): Flow<DatastorePreferences>
    }
}