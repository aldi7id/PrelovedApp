package com.preloved.app.ui.fragment.homepage.sell.preview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.PostProductResponse
import com.preloved.app.ui.fragment.homepage.sell.SellContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception

class PreviewProductViewModel(val previewProductRepository: PreviewProductRepository): BaseViewModellmpl(), PreviewProductContract.ViewModel {
    private val postProductLiveData = MutableLiveData<Resource<PostProductResponse>>()
    override fun postProductData(
        token: String,
        name: String,
        description: String,
        base_price: Int,
        category: List<Int>,
        location: String,
        image: File?
    ) {
        postProductLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = previewProductRepository.postProductData(token,name, description, base_price, category, location, image)
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