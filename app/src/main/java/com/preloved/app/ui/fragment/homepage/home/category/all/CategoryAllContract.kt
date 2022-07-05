package com.preloved.app.ui.fragment.homepage.home.category.all

import androidx.lifecycle.LiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse

interface CategoryAllContract {

    interface View : BaseContract.BaseView {
        fun getDataCategory()
    }

    interface ViewModel : BaseContract.BaseViewModel{
        fun getAllCategoryResult(): LiveData<Resource<CategoryResponse>>
        fun getDataAllCategory()
    }

    interface Repository : BaseContract.BaseRepository{
        suspend fun getDataAllCategory(): CategoryResponse
    }
}