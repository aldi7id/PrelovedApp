package com.preloved.app.ui.fragment.homepage.home.category.computer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryComputerViewModel(
    private val categoryElectronicRepository: CategoryComputerRepository
): BaseViewModellmpl(), CategoryComputerContract.ViewModel {
    private val getCategoryComputer = MutableLiveData<Resource<CategoryResponse>>()

    override fun getDataElectronicResult(): MutableLiveData<Resource<CategoryResponse>> = getCategoryComputer

    override fun getDataComputerById(categoryId: Int) {
        getCategoryComputer.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseData = categoryElectronicRepository.getDataComputerById(categoryId)
                viewModelScope.launch(Dispatchers.Main) {
                    getCategoryComputer.value = Resource.Success(responseData)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    getCategoryComputer.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }
}