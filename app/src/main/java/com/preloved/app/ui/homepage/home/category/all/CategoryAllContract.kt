package com.preloved.app.ui.homepage.home.category.all

import androidx.lifecycle.LiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.model.response.LoginResponse

interface CategoryAllContract {

    interface View : BaseContract.BaseView {
        fun getDataAllCategory()
    }

    interface ViewModel : BaseContract.BaseViewModel{
        fun getAllCategoryResult(): LiveData<Resource<LoginRequest>>
        fun getDataAllCategory()
    }

    interface Repository : BaseContract.BaseRepository{
        suspend fun getDataAllCategory(): LoginRequest
    }
}