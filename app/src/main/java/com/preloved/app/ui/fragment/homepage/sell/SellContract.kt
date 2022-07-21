package com.preloved.app.ui.fragment.homepage.sell

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.data.network.model.response.PostProductResponse
import com.preloved.app.data.network.model.response.UserResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface SellContract {
    interface View {
        fun checkFormValidation(): Boolean
        fun setDataToView(data: List<CategoryResponseItem>)
        fun getData()
        fun setOnClickListeners()
    }

    interface ViewModel {
        fun getChangeProfileResultLiveData() : MutableLiveData<Resource<PostProductResponse>>
        fun getCategoryLiveData(): MutableLiveData<Resource<List<CategoryResponseItem>>>
        fun getCategoryData()
        fun postProductData(token: String,
                            name: String,
                            description: String,
                            base_price : Int,
                            category: List<Int>,
                            location: String,
                            image : File? = null)
        fun addCategory(category: List<String>)
        fun getUserDataResult(): MutableLiveData<Resource<UserResponse>>
        fun getUserData(token: String)
        fun userSession()
        fun userSessionResult(): LiveData<DatastorePreferences>
    }

    interface Repository {
        //suspend fun saveCacheProfileData(response: UserResponse)

        suspend fun getCategoryData() : List<CategoryResponseItem>
        suspend fun postProductData(token: String,
                                    name: String,
                                    description: String,
                                    base_price : Int,
                                    category: List<Int>,
                                    location: String,
                                    image : File? = null) : PostProductResponse
        suspend fun getUserData(token: String): UserResponse
        suspend fun userSession(): Flow<DatastorePreferences>
        suspend fun deleteToken()
    }
}