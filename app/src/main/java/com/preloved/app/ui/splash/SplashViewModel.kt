package com.preloved.app.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.data.local.datastore.DatastorePreferences
import kotlinx.coroutines.launch

class SplashViewModel(
    private val splashRepository: SplashRepository
) : BaseViewModellmpl(), SplashContract.ViewModel {
    private val _userSession: MutableLiveData<DatastorePreferences> = MutableLiveData()

    override fun splashSessionResult(): LiveData<DatastorePreferences> = _userSession

    override fun splashSession() {
        viewModelScope.launch {
            splashRepository.splashSession().collect {
                _userSession.postValue(it)
            }
        }
    }

}