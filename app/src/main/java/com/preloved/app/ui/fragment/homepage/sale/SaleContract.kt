package com.preloved.app.ui.fragment.homepage.sale

import androidx.lifecycle.LiveData
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.data.network.model.response.SellerProductResponseItem
import com.preloved.app.data.network.model.response.UserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SaleContract {
    interface View {
    }
    interface ViewModel {
        fun userSession()
        fun userSessionResult(): LiveData<DatastorePreferences>
        fun getUserDataResult(): LiveData<Resource<UserResponse>>
        fun getUserData(token: String)
        fun getSellerProduct(token: String)
        fun getSellerProductResult(): LiveData<Resource<List<SellerProductResponseItem>>>
    }

    interface Repository {
        suspend fun getSellerProduct(token: String) : List<SellerProductResponseItem>
        suspend fun deleteSellerProduct(token: String, id : Int) : Response<SellerProductResponseItem>
        suspend fun getUserData(token: String): UserResponse
        suspend fun userSession(): Flow<DatastorePreferences>
    }
}