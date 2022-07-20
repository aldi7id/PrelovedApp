package com.preloved.app.ui.fragment.homepage.buyer.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.ApproveOrderResponse
import com.preloved.app.data.network.model.response.RequestApproveOrder
import com.preloved.app.data.network.model.response.SellerOrderResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class BuyerInfoViewModel(val buyerInfoRepository: BuyerInfoRepository)
    : BaseViewModellmpl(), BuyerInfoContract.ViewModel {
    private val _userSession: MutableLiveData<DatastorePreferences> = MutableLiveData()
    private val _buyerOrder: MutableLiveData<Resource<SellerOrderResponse>> = MutableLiveData()
    private val _responseOrder: MutableLiveData<Resource<ApproveOrderResponse>> = MutableLiveData()

    override fun userSession() {
        viewModelScope.launch {
            buyerInfoRepository.userSession().collect() {
                _userSession.postValue(it)
            }
        }
    }

    override fun userSessionResult(): LiveData<DatastorePreferences> = _userSession
    override fun getSellerOrderById(token: String, id: Int) {
        _buyerOrder.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = buyerInfoRepository.getSellerOrderById(token, id)
                viewModelScope.launch(Dispatchers.Main) {
                    _buyerOrder.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _buyerOrder.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun getSellerOrderByIdResult(): LiveData<Resource<SellerOrderResponse>> = _buyerOrder
    override fun statusOrder(
        token: String,
        orderId: Int,
        requestApproveOrder: RequestApproveOrder
    ) {
        _responseOrder.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = buyerInfoRepository.approveOrder(token, orderId, requestApproveOrder)
                viewModelScope.launch(Dispatchers.Main) {
                    _responseOrder.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _responseOrder.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun statusOrderResult(): LiveData<Resource<ApproveOrderResponse>> = _responseOrder
}