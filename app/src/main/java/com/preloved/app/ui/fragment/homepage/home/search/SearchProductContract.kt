package com.preloved.app.ui.fragment.homepage.home.search

import androidx.lifecycle.MutableLiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse

interface SearchProductContract {
    interface View: BaseContract.BaseView {
        fun getProduct()
    }
    interface ViewModel: BaseContract.BaseViewModel {
        fun getSearchProductResult(): MutableLiveData<Resource<CategoryResponse>>
        fun getDataSearchProduct(search: String)
    }
    interface Repository: BaseContract.BaseRepository {
        suspend fun getDataSearchProduct(search: String): CategoryResponse
    }
}