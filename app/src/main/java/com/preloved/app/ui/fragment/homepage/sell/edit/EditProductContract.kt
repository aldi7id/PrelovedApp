package com.preloved.app.ui.fragment.homepage.sell.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.io.File

interface EditProductContract {
    interface View {
        fun setOnClickListeners()
        fun setDataToView(data: SellerProductResponseItem)
        fun setDataToViewChange(data: PostProductResponse)
        fun checkFormValidation(): Boolean
    }

    interface ViewModel {
        fun userSession()
        fun userSessionResult(): LiveData<DatastorePreferences>
        fun getSellerProduct(token: String, id: Int)
        fun getSellerProductResult(): LiveData<Resource<SellerProductResponseItem>>
        fun updateResultProductData() : MutableLiveData<Resource<PostProductResponse>>
        fun updateProductData(token: String,
                              id: Int,
                            name: String,
                            description: String,
                            base_price : Int,
                            category: List<Int>,
                            location: String,
                            image : File? = null)
        fun getCategoryData()
        fun getCategoryLiveData(): MutableLiveData<Resource<List<CategoryResponseItem>>>
        fun addCategory(category: List<String>)
        fun deleteProductSeller(token: String, id: Int)
        fun deleteResultProductSeller() : MutableLiveData<Resource<Response<SellerDeleteResponse>>>
    }

    interface Repository {
        suspend fun userSession(): Flow<DatastorePreferences>
        suspend fun getProductData(token: String, id: Int): SellerProductResponseItem
        suspend fun updateProductData(token: String,
                                      id: Int,
                                    name: String,
                                    description: String,
                                    base_price : Int,
                                    category: List<Int>,
                                    location: String,
                                    image : File? = null) : PostProductResponse
        suspend fun getCategoryData() : List<CategoryResponseItem>
        suspend fun deleteSellerProduct(token: String, id: Int) : Response<SellerDeleteResponse>
    }
}