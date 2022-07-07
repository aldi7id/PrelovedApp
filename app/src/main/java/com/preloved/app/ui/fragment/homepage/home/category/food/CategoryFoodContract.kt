package com.preloved.app.ui.fragment.homepage.home.category.food

import androidx.lifecycle.LiveData
import com.preloved.app.base.arch.BaseContract
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse

interface CategoryFoodContract {

    interface View : BaseContract.BaseView {
        fun getDataFoodCategory()
    }

    interface ViewModel : BaseContract.BaseViewModel{
        fun getFoodCategoryResult(): LiveData<Resource<CategoryResponse>>
        fun getDataFoodCategory(foodId:Int)
    }

    interface Repository : BaseContract.BaseRepository{
        suspend fun getDataFoodCategory(foodId: Int): CategoryResponse
    }
}