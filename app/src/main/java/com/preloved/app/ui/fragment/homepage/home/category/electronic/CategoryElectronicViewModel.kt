package com.preloved.app.ui.fragment.homepage.home.category.electronic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryElectronicViewModel(
    private val categoryElectronicRepository: CategoryElectronicRepository
): BaseViewModellmpl(), CategoryElectronicContract.ViewModel {
    private val getCategoryElectronic = MutableLiveData<Resource<CategoryResponse>>()

    override fun getDataElectronicResult(): MutableLiveData<Resource<CategoryResponse>> = getCategoryElectronic

    override fun getDataElectronicById(electronicId: Int) {
        getCategoryElectronic.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseElectronic = categoryElectronicRepository.getDataElectronicById(electronicId)
                viewModelScope.launch(Dispatchers.Main) {
                    getCategoryElectronic.value = Resource.Success(responseElectronic)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    getCategoryElectronic.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }
}