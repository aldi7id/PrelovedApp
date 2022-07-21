package com.preloved.app.ui.fragment.homepage.home.bid

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.request.bid.BidRequest
import com.preloved.app.data.network.model.response.bid.get.GetBidResponse
import com.preloved.app.data.network.model.response.bid.post.PostBidResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PopUpBidViewModel(
    private val popUpBidRepository: PopUpBidRepository
) : BaseViewModellmpl(), PopUpBidContract.ViewModel {
    private val getDataBidProduct = MutableLiveData<Resource<GetBidResponse>>()
    private val postDataBidProduct = MutableLiveData<Resource<PostBidResponse>>()
    private val getDetailProduct = MutableLiveData<Resource<CategoryDetailResponse>>()

    override fun getBidProductOrderResult(): MutableLiveData<Resource<GetBidResponse>> = getDataBidProduct
    override fun getDetailProductResult(): MutableLiveData<Resource<CategoryDetailResponse>> = getDetailProduct
    override fun postBidProductOrderByIdResult(): MutableLiveData<Resource<PostBidResponse>> = postDataBidProduct

    override fun getDetailProductById(productId: Int) {
        getDetailProduct.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseDetail = popUpBidRepository.getDetailProductById(productId)
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

    override fun postBidProductOrderById(bidRequest: BidRequest) {
        postDataBidProduct.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val getToken = popUpBidRepository.getAccessToken()
                viewModelScope.launch(Dispatchers.Main) {
                    getToken.collect {
                        popUpBidRepository.getBidProductOrder(it.access_token)
                        val responseBid =
                            popUpBidRepository.postBidProductOrderById(it.access_token, bidRequest)
                        when (responseBid.code()) {
                            201 -> {
                                responseBid.body()?.let { data ->
                                    postDataBidProduct.value = Resource.Success(data)
                                }
                            }
                            400 -> {
                                postDataBidProduct.value = Resource.Error(null, "You has order for this product")
                            }
                            403 -> {
                                postDataBidProduct.value = Resource.Error(null, "You are not login")
                            }
                            else -> {
                                postDataBidProduct.value = Resource.Error(null, "Something went Wrong!")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    postDataBidProduct.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }
}