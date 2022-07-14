package com.preloved.app.ui.fragment.homepage.home.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailProductViewModel(
    private val detailProductRepository: DetailProductRepository
): BaseViewModellmpl(), DetailProductContract.ViewModel {
    private val getDetailProduct = MutableLiveData<Resource<CategoryDetailResponse>>()

    override fun getDetailProductResult(): MutableLiveData<Resource<CategoryDetailResponse>> = getDetailProduct

    override fun getDetailProductById(productId: Int) {
        getDetailProduct.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseDetail = detailProductRepository.getDetailProductById(productId)
                viewModelScope.launch(Dispatchers.Main) {
                    getDetailProduct.value = Resource.Success(responseDetail)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    getDetailProduct.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }
}