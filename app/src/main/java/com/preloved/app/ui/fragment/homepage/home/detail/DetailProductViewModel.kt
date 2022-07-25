package com.preloved.app.ui.fragment.homepage.home.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.BuyerOrderResponse
import com.preloved.app.data.network.model.request.wishlist.WishlistRequest
import com.preloved.app.data.network.model.response.bid.get.GetBidResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import com.preloved.app.data.network.model.response.whislist.AddWishlistResponse
import com.preloved.app.data.network.model.response.whislist.DeleteWishlistResponse
import com.preloved.app.data.network.model.response.whislist.GetWishlistResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailProductViewModel(
    private val detailProductRepository: DetailProductRepository
): BaseViewModellmpl(), DetailProductContract.ViewModel {
    private val getDetailProduct = MutableLiveData<Resource<CategoryDetailResponse>>()
    private val getDataToken = MutableLiveData<DatastorePreferences>()
    private val getDataBuyerOrder = MutableLiveData<Resource<GetBidResponse>>()
    private val _deleteOrder = MutableLiveData<Resource<BuyerOrderResponse>>()
    private val postDataWishlist = MutableLiveData<Resource<AddWishlistResponse>>()
    private val getDataWishlist = MutableLiveData<Resource<GetWishlistResponse>>()
    private val deleteDataWishlist = MutableLiveData<Resource<DeleteWishlistResponse>>()

    override fun getTokenAccessResult(): MutableLiveData<DatastorePreferences> = getDataToken
    override fun getDetailProductResult(): MutableLiveData<Resource<CategoryDetailResponse>> = getDetailProduct
    override fun getBuyerOrderResult(): MutableLiveData<Resource<GetBidResponse>> = getDataBuyerOrder
    override fun deleteBuyerOrderResult(): MutableLiveData<Resource<BuyerOrderResponse>> = _deleteOrder
    override fun postWishlistProductResult(): MutableLiveData<Resource<AddWishlistResponse>> = postDataWishlist
    override fun getWishlistProductResult(): MutableLiveData<Resource<GetWishlistResponse>> = getDataWishlist
    override fun deleteWishlistProductByIdResult(): MutableLiveData<Resource<DeleteWishlistResponse>> = deleteDataWishlist

    override fun getTokenAccess() {
        viewModelScope.launch {
            detailProductRepository.getTokenAccess().collect {
                getDataToken.postValue(it)
            }
        }
    }

    override fun getDetailProductById(productId: Int) {
        getDetailProduct.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseDetail = detailProductRepository.getDetailProductById(productId)
                viewModelScope.launch(Dispatchers.Main) {
                    getDetailProduct.value = Resource.Success(responseDetail)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    getDetailProduct.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun getBuyerOrder(tokenAccess: String) {
        getDataBuyerOrder.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = detailProductRepository.getBuyerOrder(tokenAccess)
                viewModelScope.launch(Dispatchers.Main) {
                    getDataBuyerOrder.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    getDataBuyerOrder.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun deleteBuyerOrder(token: String, id: Int) {
        _deleteOrder.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = detailProductRepository.deleteBuyerOrder(token, id)
                viewModelScope.launch(Dispatchers.Main) {
                    _deleteOrder.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _deleteOrder.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun postWishlistProduct(tokenAccess: String, wishlistRequest: WishlistRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseWishlist = detailProductRepository.postWishlistProduct(tokenAccess, wishlistRequest)
                viewModelScope.launch(Dispatchers.Main) {
                    postDataWishlist.value = Resource.Success(responseWishlist)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    postDataWishlist.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun getWishlistProduct(tokenAccess: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseWishlist = detailProductRepository.getWishlistProduct(tokenAccess)
                viewModelScope.launch(Dispatchers.Main) {
                    getDataWishlist.value = Resource.Success(responseWishlist)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    getDataWishlist.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun deleteWishlistProductById(tokenAccess: String, productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseWishlist = detailProductRepository.deleteWishlistProductById(tokenAccess, productId)
                viewModelScope.launch(Dispatchers.Main) {
                    deleteDataWishlist.value = Resource.Success(responseWishlist)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    deleteDataWishlist.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

}