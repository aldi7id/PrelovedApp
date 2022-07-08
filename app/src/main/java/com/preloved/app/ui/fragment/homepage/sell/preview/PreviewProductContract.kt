package com.preloved.app.ui.fragment.homepage.sell.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.data.network.model.response.PostProductResponse
import com.preloved.app.data.network.model.response.UserResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PreviewProductContract {
    interface View {
        fun setOnClickListeners()
    }

    interface ViewModel {
        fun postResultProductData() : MutableLiveData<Resource<PostProductResponse>>
        fun postProductData(token: String,
                            name: String,
                            description: String,
                            base_price : Int,
                            category: List<Int>,
                            location: String,
                            image : File? = null)
    }

    interface Repository {
        suspend fun postProductData(token: String,
                                    name: String,
                                    description: String,
                                    base_price : Int,
                                    category: List<Int>,
                                    location: String,
                                    image : File? = null) : PostProductResponse
    }
}