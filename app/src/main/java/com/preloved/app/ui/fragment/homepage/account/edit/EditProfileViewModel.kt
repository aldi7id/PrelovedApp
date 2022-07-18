package com.preloved.app.ui.fragment.homepage.account.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception

class EditProfileViewModel(val editProfileRepository: EditProfileRepository)
    : BaseViewModellmpl(),EditProfileContract.ViewModel {
    private val _profileLiveData = MutableLiveData<Resource<UserResponse>>()
    private val changeProfileResultLiveData = MutableLiveData<Resource<UserResponse>>()
    private val _userSession: MutableLiveData<DatastorePreferences> = MutableLiveData()

    override fun userSession() {
        viewModelScope.launch {
            editProfileRepository.userSession().collect() {
                _userSession.postValue(it)
            }
        }
    }

    override fun userSessionResult(): LiveData<DatastorePreferences> = _userSession

    override fun getChangeProfileResultLiveData(): MutableLiveData<Resource<UserResponse>> = changeProfileResultLiveData

    override fun getProfileLiveData(): MutableLiveData<Resource<UserResponse>> = _profileLiveData

    override fun getProfileData() {
        _profileLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                editProfileRepository.userSession().collect {
                    val response = editProfileRepository.getProfileData(it.access_token)
                    viewModelScope.launch(Dispatchers.Main) {
                        _profileLiveData.value = Resource.Success(response)
                }

                }
            } catch (e: Exception){
                viewModelScope.launch(Dispatchers.Main) {
                    _profileLiveData.value = Resource.Error(null,e.message.orEmpty())
                }
            }
        }
    }

    override fun updateProfileData(token: String,
                                   email: String,
                                   nama: String,
                                   city: String,
                                   address: String,
                                   phone: String,
                                   profilePhoto: File?) {
        changeProfileResultLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                    val response = editProfileRepository.updateProfileData(token, email, nama, city, address, phone, profilePhoto)
                    viewModelScope.launch(Dispatchers.Main){
                        changeProfileResultLiveData.value = Resource.Success(response)
                    }


            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    changeProfileResultLiveData.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }

    override fun logout() {
        editProfileRepository.clearSession()
    }
}