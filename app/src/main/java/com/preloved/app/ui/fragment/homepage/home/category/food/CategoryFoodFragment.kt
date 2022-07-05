package com.preloved.app.ui.fragment.homepage.home.category.food

import androidx.core.view.isVisible
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import com.preloved.app.databinding.FragmentCategoryFoodBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class CategoryFoodFragment : BaseFragment<FragmentCategoryFoodBinding, CategoryFoodViewModel>(
    FragmentCategoryFoodBinding::inflate
), CategoryFoodContract.View {
    override val viewModel: CategoryFoodViewModel by viewModel()
    override fun initView() {
        getDataFoodCategory()
        onClick()
    }

    private fun onClick() {
        getViewBinding().apply {

        }
    }

    override fun getDataFoodCategory() {
        viewModel.getDataFoodCategory(96)
    }

    override fun showLoading(isVisible: Boolean) {
        getViewBinding().pbLoading.isVisible = isVisible
    }

    override fun observeData() {
        viewModel.apply {
            getFoodCategoryResult().observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        setCategoryFood(it.data?.categories)
                    }
                    is Resource.Error -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun setCategoryFood(listCategory: List<CategoryDetailResponse.Category>?) {
        viewModel.apply {
            with(getViewBinding()) {
                val listCategory = CategoryFoodAdapter {

                }
            }
        }
    }


}