package com.preloved.app.ui.product.add

import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.data.network.model.response.PostProductResponse
import com.preloved.app.data.network.model.response.UserResponse
import java.io.File

interface AddProductContract {
    interface View {
        fun checkFormValidation(): Boolean
        fun setDataToView(data: List<CategoryResponseItem>)
        fun getData()
        fun setOnClickListeners()
    }

    interface ViewModel {
       // fun getChangeProfileResultLiveData(): MutableLiveData<Resource<UserResponse>>
        fun getCategoryLiveData(): MutableLiveData<Resource<List<CategoryResponseItem>>>
        fun getCategoryData()
        fun postProductData(name: String,
                            description: String,
                            base_price : Int,
                            category: List<CategoryResponseItem>,
                            location: String,
                            image : File? = null)
    }

    interface Repository {
        //suspend fun saveCacheProfileData(response: UserResponse)
        suspend fun getCategoryData() : List<CategoryResponseItem>
        suspend fun postProductData(name: String,
                            description: String,
                            base_price : Int,
                            category: List<CategoryResponseItem>,
                            location: String,
                            image : File? = null) : PostProductResponse
    }
}