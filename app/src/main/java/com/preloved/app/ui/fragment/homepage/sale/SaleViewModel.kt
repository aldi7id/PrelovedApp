package com.preloved.app.ui.fragment.homepage.sale

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.SellerOrderResponse
import com.preloved.app.data.network.model.response.SellerProductResponseItem
import com.preloved.app.data.network.model.response.UserResponse
import com.preloved.app.ui.fragment.homepage.account.AccountContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SaleViewModel(private val saleRepository: SaleRepository) : BaseViewModellmpl(), SaleContract.ViewModel {
    private val _userSession: MutableLiveData<DatastorePreferences> = MutableLiveData()
    private val _getUserData = MutableLiveData<Resource<UserResponse>>()
    private val _product: MutableLiveData<Resource<List<SellerProductResponseItem>>> = MutableLiveData()
    private val _order: MutableLiveData<Resource<List<SellerOrderResponse>>> = MutableLiveData()
    private val _status: MutableLiveData<Resource<List<SellerOrderResponse>>> = MutableLiveData()

    override fun userSession() {
        viewModelScope.launch {
            saleRepository.userSession().collect() {
                _userSession.postValue(it)
            }
        }
    }

    override fun userSessionResult(): LiveData<DatastorePreferences> = _userSession

    override fun getUserDataResult(): LiveData<Resource<UserResponse>> = _getUserData

    override fun getUserData(token: String) {
        _getUserData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = saleRepository.getUserData(token)
                viewModelScope.launch(Dispatchers.Main) {
                    _getUserData.value = Resource.Success(response)
                }

            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _getUserData.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun getSellerProduct(token: String) {
        _product.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = saleRepository.getSellerProduct(token)
                viewModelScope.launch(Dispatchers.Main) {
                    _product.value = Resource.Success(response)
                }

            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _product.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun getSellerProductResult(): LiveData<Resource<List<SellerProductResponseItem>>> = _product
    override fun getSellerProductOrder(token: String) {
        _order.value = Resource.Loading()
        viewModelScope.launch {
            try {
                viewModelScope.launch(Dispatchers.Main){
                    _order.value = Resource.Success(saleRepository.getSellerProductOrder(token))
                }
            } catch (e: Exception){
                viewModelScope.launch(Dispatchers.Main) {
                    _order.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun getSellerProductOrderResult(): LiveData<Resource<List<SellerOrderResponse>>> = _order
    override fun getSellerProductOrderAccepted(token: String, status: String) {
        _status.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val response = saleRepository.getSellerProductOrderAccepted(token,status)
                viewModelScope.launch(Dispatchers.Main){
                    _status.value = Resource.Success(response)
                }
            } catch (e: Exception){
                viewModelScope.launch(Dispatchers.Main) {
                    _status.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun getSellerProductOrderAcceptedResult(): LiveData<Resource<List<SellerOrderResponse>>> = _status


}