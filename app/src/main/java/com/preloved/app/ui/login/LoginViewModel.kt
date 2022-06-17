package com.preloved.app.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : BaseViewModellmpl(), LoginContract.ViewModel {
    private val postData = MutableLiveData<Resource<LoginResponse>>()

    override fun postLoginUserResult(): LiveData<Resource<LoginResponse>> = postData

    override fun postLoginDataUser(loginRequest: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseDataUser = loginRepository.postLoginDataUser(loginRequest)
                viewModelScope.launch(Dispatchers.Main) {
                    postData.value = Resource.Success(responseDataUser)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    postData.value = Resource.Error(null, e.message.orEmpty())
                }
            }
        }
    }
}