package com.preloved.app.ui.fragment.homepage.home.category.food

import androidx.core.view.isVisible
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.databinding.FragmentCategoryFoodBinding
import com.preloved.app.ui.fragment.homepage.home.category.CategoryAllAdapter
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
        viewModel.getDataFoodCategory(105)
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
                        setCategoryFood(it.data)
                    }
                    is Resource.Error -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun setCategoryFood(data: CategoryResponse?) {
        viewModel.apply {
            with(getViewBinding()) {
                val listCategoryAdapter = CategoryAllAdapter {

                }
                listCategoryAdapter.submitList(data)
                rvCategory.adapter = listCategoryAdapter
            }
        }
    }

}