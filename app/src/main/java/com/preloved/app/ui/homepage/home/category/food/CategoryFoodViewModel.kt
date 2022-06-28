package com.preloved.app.ui.homepage.home.category.food

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.request.auth.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryFoodViewModel(private val categoryFoodRepository: CategoryFoodRepository) :
    BaseViewModellmpl(), CategoryFoodContract.ViewModel {
    private val getCategoryFood = MutableLiveData<Resource<LoginRequest>>()
    override fun getFoodCategoryResult(): LiveData<Resource<LoginRequest>> = getCategoryFood


    override fun getDataFoodCategory(foodId:Int) {
        getCategoryFood.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responsCategory = categoryFoodRepository.getDataFoodCategory(foodId)
                viewModelScope.launch(Dispatchers.Main) {
                    getCategoryFood.value = Resource.Success(responsCategory)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    getCategoryFood.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }
}