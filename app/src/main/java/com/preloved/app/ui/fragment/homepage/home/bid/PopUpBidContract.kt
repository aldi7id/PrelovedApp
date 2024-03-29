package com.preloved.app.ui.fragment.homepage.home.bid

import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.request.bid.BidRequest
import com.preloved.app.data.network.model.response.bid.get.GetBidResponse
import com.preloved.app.data.network.model.response.bid.post.PostBidResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface PopUpBidContract {
    interface ViewModel: BaseContract.BaseViewModel {
        fun getBidProductOrderResult(): MutableLiveData<Resource<GetBidResponse>>
        fun getDetailProductResult(): MutableLiveData<Resource<CategoryDetailResponse>>
        fun postBidProductOrderByIdResult(): MutableLiveData<Resource<PostBidResponse>>
        fun getDetailProductById(productId: Int)
        fun postBidProductOrderById(bidRequest: BidRequest)
    }
    interface Repository: BaseContract.BaseRepository {
        suspend fun getDetailProductById(productId: Int): CategoryDetailResponse
        suspend fun getBidProductOrder(accessToken: String): GetBidResponse
        suspend fun postBidProductOrderById(accessToken: String, bidRequest: BidRequest): Response<PostBidResponse>
        suspend fun getAccessToken(): Flow<DatastorePreferences>
    }
}