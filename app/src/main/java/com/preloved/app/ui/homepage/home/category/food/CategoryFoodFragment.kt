package com.preloved.app.ui.homepage.home.category.food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.databinding.FragmentCategoryAllBinding
import com.preloved.app.databinding.FragmentCategoryFoodBinding
import com.preloved.app.ui.homepage.home.category.all.CategoryAllContract
import com.preloved.app.ui.homepage.home.category.all.CategoryAllViewModel
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