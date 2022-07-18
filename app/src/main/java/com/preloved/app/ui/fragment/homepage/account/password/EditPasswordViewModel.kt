package com.preloved.app.ui.fragment.homepage.account.password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.UpdatePasswordResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class EditPasswordViewModel(val editPasswordRepository: EditPasswordRepository) :
    BaseViewModellmpl(), EditPasswordContract.ViewModel {
    private val _userSession: MutableLiveData<DatastorePreferences> = MutableLiveData()
    private val _update: MutableLiveData<Resource<UpdatePasswordResponse>> = MutableLiveData()

    override fun userSession() {
        viewModelScope.launch {
            editPasswordRepository.userSession().collect() {
                _userSession.postValue(it)
            }
        }
    }

    override fun userSessionResult(): LiveData<DatastorePreferences> = _userSession

    override  fun updatePassword(
        token: String,
        oldPassword: String,
        newPassword: String,
        rePassword: String
    ) {
        _update.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = editPasswordRepository.updatePassword(token, oldPassword, newPassword, rePassword)
                viewModelScope.launch(Dispatchers.Main) {
                    _update.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    _update.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun updateResultPassword(): MutableLiveData<Resource<UpdatePasswordResponse>> = _update
}