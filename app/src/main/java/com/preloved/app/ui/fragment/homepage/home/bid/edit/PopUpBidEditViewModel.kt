package com.preloved.app.ui.fragment.homepage.home.bid.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.request.bid.BidRequest
import com.preloved.app.data.network.model.response.BuyerOrderEditResponse
import com.preloved.app.data.network.model.response.bid.get.GetBidResponse
import com.preloved.app.data.network.model.response.bid.post.PostBidResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import com.preloved.app.ui.fragment.homepage.home.bid.PopUpBidContract
import com.preloved.app.ui.fragment.homepage.home.bid.PopUpBidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PopUpBidEditViewModel(
    private val popUpBidEditRepository: PopUpBidEditRepository
) : BaseViewModellmpl(), PopUpBidEditContract.ViewModel {
    private val _bidProduct = MutableLiveData<Resource<GetBidResponse>>()
    private val _postBid = MutableLiveData<Resource<BuyerOrderEditResponse>>()
    private val _detailProduct = MutableLiveData<Resource<CategoryDetailResponse>>()
    private val _token = MutableLiveData<DatastorePreferences>()

    override fun getBidProductOrderResult(): MutableLiveData<Resource<GetBidResponse>> = _bidProduct

    override fun getDetailProductResult(): MutableLiveData<Resource<CategoryDetailResponse>> = _detailProduct

    override fun postBidProductOrderByIdResult(): MutableLiveData<Resource<BuyerOrderEditResponse>> = _postBid

    override fun getDetailProductById(productId: Int) {
        _detailProduct.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseDetail = popUpBidEditRepository.getDetailProductById(productId)
                viewModelScope.launch(Dispatchers.Main) {
                    _detailProduct.value = Resource.Success(responseDetail)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _detailProduct.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun postBidProductOrderById(token: String, id: Int, bid_price : Int) {
        _postBid.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = popUpBidEditRepository.postBidProductOrderById(token, id, bid_price)
                viewModelScope.launch(Dispatchers.Main) {
                    _postBid.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _postBid.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun getTokenAccess() {
        viewModelScope.launch {
            popUpBidEditRepository.getAccessToken().collect() {
                _token.postValue(it)
            }
        }
    }

    override fun getTokenAccessResult(): MutableLiveData<DatastorePreferences> = _token

}