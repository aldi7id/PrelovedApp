package com.preloved.app.ui.fragment.homepage.sell

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.data.network.model.response.PostProductResponse
import com.preloved.app.data.network.model.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception

class SellViewModel(val sellRepository: SellRepository): BaseViewModellmpl(), SellContract.ViewModel {
    private val categoryLiveData = MutableLiveData<Resource<List<CategoryResponseItem>>>()
    private val postProductLiveData = MutableLiveData<Resource<PostProductResponse>>()
    private var _categoryList = MutableLiveData<List<String>>()
    val categoryList : LiveData<List<String>> get() = _categoryList
    private val _getUserData = MutableLiveData<Resource<UserResponse>>()
    override fun getUserDataResult(): MutableLiveData<Resource<UserResponse>> = _getUserData

    private val _userSession: MutableLiveData<DatastorePreferences> = MutableLiveData()
    override fun userSessionResult(): LiveData<DatastorePreferences> = _userSession

    override fun getUserData(token: String) {
        _getUserData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = sellRepository.getUserData(token)
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

    override fun userSession() {
        viewModelScope.launch {
            sellRepository.userSession().collect() {
                _userSession.postValue(it)
            }
        }
    }

    override fun getChangeProfileResultLiveData(): MutableLiveData<Resource<PostProductResponse>> = postProductLiveData

    override fun getCategoryLiveData(): MutableLiveData<Resource<List<CategoryResponseItem>>> = categoryLiveData

    override fun getCategoryData() {
        categoryLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = sellRepository.getCategoryData()
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
                val response = sellRepository.postProductData(token,name, description, base_price, category, location, image)
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

    override fun addCategory(category: List<String>) {
        _categoryList.postValue(category)
    }


}