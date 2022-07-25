package com.preloved.app.ui.fragment.homepage.account.wishlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.whislist.GetWishlistResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val wishlistRepository: WishlistRepository
): BaseViewModellmpl(), WishlistContract.ViewModel {
    private val getDataToken = MutableLiveData<DatastorePreferences>()
    private val getDataWishlist = MutableLiveData<Resource<GetWishlistResponse>>()

    override fun getTokenAccessResult(): MutableLiveData<DatastorePreferences> = getDataToken
    override fun getWishlistProductResult(): MutableLiveData<Resource<GetWishlistResponse>> = getDataWishlist

    override fun getTokenAccess() {
        viewModelScope.launch {
            wishlistRepository.getAccessToken().collect {
                getDataToken.postValue(it)
            }
        }
    }

    override fun getWishlistProduct(tokenAccess: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseWishlist = wishlistRepository.getWishlistProduct(tokenAccess)
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
}