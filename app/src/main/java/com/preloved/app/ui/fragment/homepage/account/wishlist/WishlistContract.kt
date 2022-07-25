package com.preloved.app.ui.fragment.homepage.account.wishlist

import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.whislist.GetWishlistResponse
import kotlinx.coroutines.flow.Flow

interface WishlistContract {
    interface View: BaseContract.BaseView {
        fun getData()
    }
    interface ViewModel: BaseContract.BaseViewModel {
        fun getTokenAccessResult(): MutableLiveData<DatastorePreferences>
        fun getWishlistProductResult(): MutableLiveData<Resource<GetWishlistResponse>>
        fun getTokenAccess()
        fun getWishlistProduct(tokenAccess: String)
    }
    interface Repository: BaseContract.BaseRepository {
        suspend fun getAccessToken(): Flow<DatastorePreferences>
        suspend fun getWishlistProduct(accessToken: String): GetWishlistResponse
    }
}