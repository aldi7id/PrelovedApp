package com.preloved.app.ui.fragment.homepage.account.myorder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.BuyerOrderResponse
import com.preloved.app.ui.fragment.homepage.sale.SaleContract
import com.preloved.app.ui.fragment.homepage.sale.SaleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyOrderViewModel(private val myOrderRepository: MyOrderRepository) : BaseViewModellmpl(), MyOrderContract.ViewModel {
    private val _userSession: MutableLiveData<DatastorePreferences> = MutableLiveData()
    private val _myOrder: MutableLiveData<Resource<List<BuyerOrderResponse>>> = MutableLiveData()
    private val _deleteMyOrder: MutableLiveData<Resource<BuyerOrderResponse>> = MutableLiveData()

    override fun userSession() {
        viewModelScope.launch {
            myOrderRepository.userSession().collect() {
                _userSession.postValue(it)
            }
        }
    }

    override fun userSessionResult(): LiveData<DatastorePreferences> = _userSession

    override fun getBuyerOrder(token: String) {
        _myOrder.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = myOrderRepository.getBuyerOrder(token)
                viewModelScope.launch(Dispatchers.Main) {
                    _myOrder.value = Resource.Success(response)
                }

            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _myOrder.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun getBuyerOrderResult(): MutableLiveData<Resource<List<BuyerOrderResponse>>> = _myOrder

    override fun deleteBuyerOrder(token: String, id: Int) {
        _deleteMyOrder.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = myOrderRepository.deleteBuyerOrder(token, id)
                viewModelScope.launch(Dispatchers.Main) {
                    _deleteMyOrder.value = Resource.Success(response)
                }

            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _deleteMyOrder.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun deleteBuyerOrderResult(): MutableLiveData<Resource<BuyerOrderResponse>> = _deleteMyOrder
}