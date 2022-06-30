package com.preloved.app.ui.fragment.homepage.home.category.food

import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
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

    }

    override fun observeData() {
        viewModel.apply {
            getFoodCategoryResult().observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {

                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }



}