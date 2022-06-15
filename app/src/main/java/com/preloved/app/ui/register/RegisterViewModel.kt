package com.preloved.app.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.load.engine.Resource
import com.preloved.app.base.arch.BaseViewModellmpl
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(
    private val registerRepository: RegisterRepository
): BaseViewModellmpl(), RegisterContract.ViewModel {
    private val registerUserLiveData = MutableLiveData<Resource<Unit>>()

    override fun getRegisterUserLiveData(): LiveData<Resource<Unit>> = registerUserLiveData

//    override fun registerUser(userEntity: UserEntity) {
//        registerUserLiveData.value = Resource.Loading()
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val responseRegisterUser = registerRepository.postRegisterUser(userEntity)
//                viewModelScope.launch(Dispatchers.Main) {
//                    registerUserLiveData.value = Resource.Success(responseRegisterUser)
//                }
//            } catch (e: Exception) {
//                viewModelScope.launch(Dispatchers.Main) {
//                    registerUserLiveData.value = Resource.Error(null, e.message.orEmpty())
//                }
//            }
//        }
//    }
}