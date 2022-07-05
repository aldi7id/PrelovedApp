package com.preloved.app.ui.fragment.homepage.sell

import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.data.network.model.response.PostProductResponse
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
        fun postProductData(name: String,
                            description: String,
                            base_price : Int,
                            category: Int,
                            location: String,
                            image : File? = null)
        fun addCategory(category: List<String>)
    }

    interface Repository {
        //suspend fun saveCacheProfileData(response: UserResponse)

        suspend fun getCategoryData() : List<CategoryResponseItem>
        suspend fun postProductData(name: String,
                                    description: String,
                                    base_price : Int,
                                    category: Int,
                                    location: String,
                                    image : File? = null) : PostProductResponse
        suspend fun category() : List<String>
    }
}