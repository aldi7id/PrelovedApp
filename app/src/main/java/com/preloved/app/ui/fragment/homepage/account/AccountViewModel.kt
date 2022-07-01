package com.preloved.app.ui.fragment.homepage.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.ui.fragment.register.RegisterContract
import com.preloved.app.ui.fragment.register.RegisterRepository
import kotlinx.coroutines.launch

class AccountViewModel(private val accountRepository: AccountRepository
): BaseViewModellmpl(), AccountContract.ViewModel {
    private val _checkLogin = MutableLiveData<String>()
    private val _userSession: MutableLiveData<DatastorePreferences> = MutableLiveData()
    override fun userSession() {
       viewModelScope.launch {
           accountRepository.userSession().collect() {
               _userSession.postValue(it)
           }
       }
    }

    override fun userSessionResult(): LiveData<DatastorePreferences> = _userSession

    override fun checkLogin(): LiveData<String> = _checkLogin

    override fun getToken() {
//        viewModelScope.launch {
//            accountRepository.getToken().
//        }
    }
}