package com.preloved.app.ui.fragment.homepage.home.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchProductViewModel(
    private val searchProductRepository: SearchProductRepository
): BaseViewModellmpl(), SearchProductContract.ViewModel {
    private val getDataSearch = MutableLiveData<Resource<CategoryResponse>>()

    override fun getSearchProductResult(): MutableLiveData<Resource<CategoryResponse>> = getDataSearch

    override fun getDataSearchProduct(search: String) {
        getDataSearch.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseSearch = searchProductRepository.getDataSearchProduct(search)
                viewModelScope.launch(Dispatchers.Main) {
                    getDataSearch.value = Resource.Success(responseSearch)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    getDataSearch.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }
}