package com.preloved.app.ui.fragment.homepage.sell.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.data.network.model.response.PostProductResponse
import com.preloved.app.data.network.model.response.SellerDeleteResponse
import com.preloved.app.data.network.model.response.SellerProductResponseItem
import com.preloved.app.ui.profile.edit.EditProfileContract
import com.preloved.app.ui.profile.edit.EditProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.File
import java.lang.Exception

class EditProductViewModel(val editProductRepository: EditProductRepository)
    : BaseViewModellmpl(), EditProductContract.ViewModel {
    private val categoryLiveData = MutableLiveData<Resource<List<CategoryResponseItem>>>()
    private val _userSession: MutableLiveData<DatastorePreferences> = MutableLiveData()
    private val _product: MutableLiveData<Resource<SellerProductResponseItem>> = MutableLiveData()
    private val _updateProduct: MutableLiveData<Resource<PostProductResponse>> = MutableLiveData()
    private val _deleteProduct: MutableLiveData<Resource<Response<SellerDeleteResponse>>> = MutableLiveData()
    private var _categoryList = MutableLiveData<List<String>>()
    val categoryList : LiveData<List<String>> get() = _categoryList

    override fun deleteResultProductSeller(): MutableLiveData<Resource<Response<SellerDeleteResponse>>> = _deleteProduct
    override fun deleteProductSeller(token: String, id: Int) {
        _deleteProduct.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = editProductRepository.deleteSellerProduct(token, id)
                viewModelScope.launch(Dispatchers.Main) {
                    _deleteProduct.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _deleteProduct.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }

    }
    override fun userSession() {
        viewModelScope.launch {
            editProductRepository.userSession().collect() {
                _userSession.postValue(it)
            }
        }
    }

    override fun userSessionResult(): LiveData<DatastorePreferences> = _userSession
    override fun getSellerProduct(token: String, id: Int) {
        _product.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = editProductRepository.getProductData(token, id)
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

    override fun getSellerProductResult(): LiveData<Resource<SellerProductResponseItem>> = _product

    override fun updateResultProductData(): MutableLiveData<Resource<PostProductResponse>> = _updateProduct

    override fun updateProductData(
        token: String,
        id: Int,
        name: String,
        description: String,
        base_price: Int,
        category: List<Int>,
        location: String,
        image: File?
    ) {
        _updateProduct.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = editProductRepository.updateProductData(token, id, name, description, base_price, category, location,image)
                viewModelScope.launch(Dispatchers.Main){
                    _updateProduct.value = Resource.Success(response)
                }


            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _updateProduct.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun getCategoryData() {

        categoryLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = editProductRepository.getCategoryData()
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

    override fun getCategoryLiveData(): MutableLiveData<Resource<List<CategoryResponseItem>>> = categoryLiveData

    override fun addCategory(category: List<String>) {
        _categoryList.postValue(category)
    }
}