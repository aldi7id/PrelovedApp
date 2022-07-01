package com.preloved.app.ui.profile.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.LoginResponse
import com.preloved.app.data.network.model.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception

class EditProfileViewModel(val repository: EditProfileRepository): BaseViewModellmpl(),EditProfileContract.ViewModel {
    private val profileLiveData = MutableLiveData<Resource<UserResponse>>()
    private val changeProfileResultLiveData = MutableLiveData<Resource<UserResponse>>()
    override fun getChangeProfileResultLiveData(): MutableLiveData<Resource<UserResponse>> = changeProfileResultLiveData

    override fun getProfileLiveData(): MutableLiveData<Resource<UserResponse>> = profileLiveData

    override fun getProfileData() {
        profileLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getProfileData()
                viewModelScope.launch(Dispatchers.Main) {
                    profileLiveData.value = Resource.Success(response)
                }
            } catch (e: Exception){
                viewModelScope.launch(Dispatchers.Main) {
                    profileLiveData.value = Resource.Error(null,e.message.orEmpty())
                }
            }
        }
    }

    override fun updateProfileData(email: String,
                                   nama: String,
                                   city: String,
                                   address: String,
                                   phone: String,
                                   profilePhoto: File?) {
        changeProfileResultLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.updateProfileData(email,nama,city,address, phone, profilePhoto)
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
        repository.clearSession()
    }
}