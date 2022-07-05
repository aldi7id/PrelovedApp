package com.preloved.app.ui.fragment.homepage.home.category.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryAllViewModel(
    private val categoryAllRepository: CategoryAllRepository
) : BaseViewModellmpl(), CategoryAllContract.ViewModel {
    private val getCategoryAll = MutableLiveData<Resource<CategoryResponse>>()
    override fun getAllCategoryResult(): LiveData<Resource<CategoryResponse>> = getCategoryAll

    override fun getDataAllCategory() {
        getCategoryAll.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseCategory = categoryAllRepository.getDataAllCategory()
                viewModelScope.launch(Dispatchers.Main) {
                    getCategoryAll.value = Resource.Success(responseCategory)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    getCategoryAll.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }
}