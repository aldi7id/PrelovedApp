package com.preloved.app.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.data.local.datasource.LocalDataSourceImpl
import com.preloved.app.data.local.datastore.DatastorePreferences
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashViewModel(private val localDataSourceImpl: LocalDataSourceImpl): BaseViewModellmpl(), SplashContract.ViewModel {

    private val _userSession: MutableLiveData<DatastorePreferences> = MutableLiveData()

    override fun SplashSession() {
        viewModelScope.launch {
            localDataSourceImpl.getUserSession().collect() {
                _userSession.postValue(it)
            }
        }
    }

    override fun SplashSessionResult(): LiveData<DatastorePreferences> = _userSession

}