package com.preloved.app.ui.fragment.homepage.home.detail

import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.BuyerOrderResponse
import com.preloved.app.data.network.model.request.wishlist.WishlistRequest
import com.preloved.app.data.network.model.response.bid.get.GetBidResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import com.preloved.app.data.network.model.response.whislist.AddWishlistResponse
import com.preloved.app.data.network.model.response.whislist.DeleteWishlistResponse
import com.preloved.app.data.network.model.response.whislist.GetWishlistResponse
import kotlinx.coroutines.flow.Flow

interface DetailProductContract {
    interface View: BaseContract.BaseView {
        fun getDataDetail()
    }
    interface ViewModel: BaseContract.BaseViewModel {
        fun getTokenAccessResult(): MutableLiveData<DatastorePreferences>
        fun getDetailProductResult(): MutableLiveData<Resource<CategoryDetailResponse>>
        fun getBuyerOrderResult(): MutableLiveData<Resource<GetBidResponse>>
        fun deleteBuyerOrderResult() : MutableLiveData<Resource<BuyerOrderResponse>>
        fun postWishlistProductResult(): MutableLiveData<Resource<AddWishlistResponse>>
        fun getWishlistProductResult(): MutableLiveData<Resource<GetWishlistResponse>>
        fun deleteWishlistProductByIdResult(): MutableLiveData<Resource<DeleteWishlistResponse>>
        fun getTokenAccess()
        fun getDetailProductById(productId: Int)
        fun getBuyerOrder(tokenAccess: String)
        fun deleteBuyerOrder(token: String, id: Int)
        fun postWishlistProduct(tokenAccess: String, wishlistRequest: WishlistRequest)
        fun getWishlistProduct(tokenAccess: String)
        fun deleteWishlistProductById(tokenAccess: String, productId: Int)
    }
    interface Repository: BaseContract.BaseRepository {
        suspend fun getTokenAccess(): Flow<DatastorePreferences>
        suspend fun getDetailProductById(productId: Int): CategoryDetailResponse
        suspend fun getBuyerOrder(tokenAccess: String): GetBidResponse
        suspend fun deleteBuyerOrder(token: String, id: Int): BuyerOrderResponse
        suspend fun postWishlistProduct(tokenAccess: String, wishlistRequest: WishlistRequest): AddWishlistResponse
        suspend fun getWishlistProduct(tokenAccess: String): GetWishlistResponse
        suspend fun deleteWishlistProductById(tokenAccess: String, productId: Int): DeleteWishlistResponse
    }
}