package com.preloved.app.ui.fragment.homepage.home.category.souvenir

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategorySouvenirViewModel(
    private val categorySouvenirRepository: CategorySouvenirRepository
): BaseViewModellmpl(), CategorySouvenirContract.ViewModel {
    private val getCategory = MutableLiveData<Resource<CategoryResponse>>()

    override fun getDataCategoryResult(): MutableLiveData<Resource<CategoryResponse>> = getCategory

    override fun getDataById(categoryId: Int) {
        getCategory.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseData = categorySouvenirRepository.getDataCategoryById(categoryId)
                viewModelScope.launch(Dispatchers.Main) {
                    getCategory.value = Resource.Success(responseData)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    getCategory.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }
}