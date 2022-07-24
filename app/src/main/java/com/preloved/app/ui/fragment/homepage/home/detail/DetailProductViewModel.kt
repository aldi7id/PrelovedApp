package com.preloved.app.ui.fragment.homepage.home.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.BuyerOrderResponse
import com.preloved.app.data.network.model.response.bid.get.GetBidResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailProductViewModel(
    private val detailProductRepository: DetailProductRepository
): BaseViewModellmpl(), DetailProductContract.ViewModel {
    private val getDetailProduct = MutableLiveData<Resource<CategoryDetailResponse>>()
    private val getDataToken = MutableLiveData<DatastorePreferences>()
    private val getDataBuyerOrder = MutableLiveData<Resource<GetBidResponse>>()
    private val _deleteOrder = MutableLiveData<Resource<BuyerOrderResponse>>()

    override fun getTokenAccessResult(): MutableLiveData<DatastorePreferences> = getDataToken
    override fun getDetailProductResult(): MutableLiveData<Resource<CategoryDetailResponse>> = getDetailProduct
    override fun getBuyerOrderResult(): MutableLiveData<Resource<GetBidResponse>> = getDataBuyerOrder

    override fun getTokenAccess() {
        viewModelScope.launch {
            detailProductRepository.getTokenAccess().collect {
                getDataToken.postValue(it)
            }
        }
    }

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

    override fun getBuyerOrder(tokenAccess: String) {
        getDataBuyerOrder.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = detailProductRepository.getBuyerOrder(tokenAccess)
                viewModelScope.launch(Dispatchers.Main) {
                    getDataBuyerOrder.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    getDataBuyerOrder.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun deleteBuyerOrder(token: String, id: Int) {
        _deleteOrder.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = detailProductRepository.deleteBuyerOrder(token, id)
                viewModelScope.launch(Dispatchers.Main) {
                    _deleteOrder.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _deleteOrder.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun deleteBuyerOrderResult(): MutableLiveData<Resource<BuyerOrderResponse>> = _deleteOrder
}