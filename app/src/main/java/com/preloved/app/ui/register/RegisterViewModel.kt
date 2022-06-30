package com.preloved.app.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.data.network.model.response.LoginResponse
import com.preloved.app.data.network.model.response.RegisterResponse
import com.preloved.app.data.network.model.response.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerRepository: RegisterRepository
): BaseViewModellmpl(), RegisterContract.ViewModel {
    private val registerUserLiveData = MutableLiveData<Resource<RegisterResponse>>()

    override fun getRegisterUserLiveData(): LiveData<Resource<RegisterResponse>> = registerUserLiveData

    override fun registerUser(registerRequest: RegisterRequest) {
        registerUserLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = registerRepository.postRegisterUser(registerRequest)
                viewModelScope.launch(Dispatchers.Main) {
                   registerUserLiveData.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    registerUserLiveData.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }
}