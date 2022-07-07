package com.preloved.app.ui.fragment.homepage.home.category.electronic

import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse

interface CategoryElectronicContract {
    interface View: BaseContract.BaseView {
        fun getCategoryElectronic()
    }
    interface ViewModel: BaseContract.BaseViewModel {
        fun getDataElectronicResult(): MutableLiveData<Resource<CategoryResponse>>
        fun getDataElectronicById(electronicId: Int)
    }
    interface Repository: BaseContract.BaseRepository {
        suspend fun getDataElectronicById(electronicId: Int): CategoryResponse
    }
}