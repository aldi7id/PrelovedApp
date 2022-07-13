package com.preloved.app.ui.fragment.homepage.home.detail

import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse

interface DetailProductContract {
    interface View: BaseContract.BaseView {
        fun getDataDetail()
    }
    interface ViewModel: BaseContract.BaseViewModel {
        fun getDetailProductResult(): MutableLiveData<Resource<CategoryDetailResponse>>
        fun getDetailProductById(productId: Int)
    }
    interface Repository: BaseContract.BaseRepository {
        suspend fun getDetailProductById(productId: Int): CategoryDetailResponse
    }
}