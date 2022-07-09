package com.preloved.app.ui.fragment.homepage.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.preloved.app.base.arch.BaseViewModellmpl
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.NotificationResponse
import com.preloved.app.ui.fragment.homepage.sell.preview.PreviewProductContract
import com.preloved.app.ui.fragment.homepage.sell.preview.PreviewProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class NotificationViewModel(val notificationRepository: NotificationRepository): BaseViewModellmpl(), NotificationContract.ViewModel {
    private val _userSession: MutableLiveData<DatastorePreferences> = MutableLiveData()
    private val _notification: MutableLiveData<Resource<List<NotificationResponse>>> = MutableLiveData()
    override fun userSession() {
        viewModelScope.launch {
            notificationRepository.userSession().collect() {
                _userSession.postValue(it)
            }
        }
    }

    override fun userSessionResult(): LiveData<DatastorePreferences> = _userSession
    override fun getNotification(token: String) {
        _notification.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = notificationRepository.getNotification(token)
                viewModelScope.launch(Dispatchers.Main) {
                    _notification.value = Resource.Success(response)
                }
            } catch (e: Exception){
                viewModelScope.launch(Dispatchers.Main) {
                    _notification.value = Resource.Error(null,e.message.orEmpty())
                }
            }
        }
    }

    override fun getNotificationResult(): MutableLiveData<Resource<List<NotificationResponse>>> = _notification
}