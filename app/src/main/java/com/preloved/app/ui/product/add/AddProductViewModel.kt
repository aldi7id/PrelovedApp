package com.preloved.app.ui.product.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.data.network.model.response.LoginResponse
import com.preloved.app.data.network.model.response.PostProductResponse
import com.preloved.app.data.network.model.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception

class AddProductViewModel(val repository: AddProductRepository) : BaseViewModellmpl(), AddProductContract.ViewModel {
    private val categoryLiveData = MutableLiveData<Resource<List<CategoryResponseItem>>>()
    private val postProductLiveData = MutableLiveData<Resource<PostProductResponse>>()
    override fun getCategoryLiveData(): MutableLiveData<Resource<List<CategoryResponseItem>>> = categoryLiveData

    override fun getCategoryData() {
        categoryLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getCategoryData()
                viewModelScope.launch(Dispatchers.Main) {
                    categoryLiveData.value = Resource.Success(response)
                }
            } catch (e: Exception){
                viewModelScope.launch(Dispatchers.Main) {
                    categoryLiveData.value = Resource.Error(null,e.message.orEmpty())
                }
            }
        }
    }

    override fun postProductData(
        name: String,
        description: String,
        base_price: Int,
        category: List<CategoryResponseItem>,
        location: String,
        image: File?
    ) {
        postProductLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.postProductData(name, description, base_price, category, location, image)
                viewModelScope.launch(Dispatchers.Main) {
                    postProductLiveData.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    postProductLiveData.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }
}