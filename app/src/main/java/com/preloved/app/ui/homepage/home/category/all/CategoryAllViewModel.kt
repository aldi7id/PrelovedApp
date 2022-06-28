package com.preloved.app.ui.homepage.home.category.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryAllViewModel(private val categoryAllRepository: CategoryAllRepository) :
    BaseViewModellmpl(), CategoryAllContract.ViewModel {
    private val getCategoryAll = MutableLiveData<Resource<LoginRequest>>()
    override fun getAllCategoryResult(): LiveData<Resource<LoginRequest>> = getCategoryAll


    override fun getDataAllCategory() {
        getCategoryAll.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responsCategory = categoryAllRepository.getDataAllCategory()
                viewModelScope.launch(Dispatchers.Main) {
                    getCategoryAll.value = Resource.Success(responsCategory)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    getCategoryAll.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }
}