package com.preloved.app.ui.homepage.home.category.all

import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.databinding.FragmentCategoryAllBinding


class CategoryAllFragment : BaseFragment<FragmentCategoryAllBinding, CategoryAllViewModel>(
    FragmentCategoryAllBinding::inflate
), CategoryAllContract.View {
    override fun initView() {
        getDataAllCategory()
        onClick()
    }

    private fun onClick() {
        getViewBinding().apply {

        }
    }

    override fun getDataAllCategory() {
        getViewModel().apply {

        }
    }

    override fun observeData() {
        getViewModel().apply {
            getAllCategoryResult().observe(viewLifecycleOwner) {
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