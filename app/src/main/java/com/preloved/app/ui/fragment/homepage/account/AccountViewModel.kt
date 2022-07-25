package com.preloved.app.ui.fragment.homepage.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.UserResponse
import com.preloved.app.ui.fragment.register.RegisterContract
import com.preloved.app.ui.fragment.register.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(private val accountRepository: AccountRepository
): BaseViewModellmpl(), AccountContract.ViewModel {
    private val _checkLogin = MutableLiveData<String>()
    private val _userSession: MutableLiveData<DatastorePreferences> = MutableLiveData()
    private val _getUserData = MutableLiveData<Resource<UserResponse>>()

    override fun userSession() {
       viewModelScope.launch {
           accountRepository.userSession().collect() {
               _userSession.postValue(it)
           }
       }
    }

    override fun userSessionResult(): LiveData<DatastorePreferences> = _userSession

    override fun checkLogin(): LiveData<String> = _checkLogin
    override fun getUserDataResult(): LiveData<Resource<UserResponse>> = _getUserData

    override fun getUserData(token: String) {
        _getUserData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = accountRepository.getUserData(token)
                    viewModelScope.launch(Dispatchers.Main) {
                        _getUserData.value = Resource.Success(response)
                    }

            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                   _getUserData.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun deleteToken() {
        viewModelScope.launch {
            accountRepository.deleteToken()
        }
    }

}