package com.preloved.app.ui.fragment.homepage.home.category.man.cloth

import androidx.core.view.isVisible
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.databinding.FragmentCategoryClothMenBinding
import com.preloved.app.ui.fragment.homepage.home.category.CategoryAllAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryClothMenFragment : BaseFragment<FragmentCategoryClothMenBinding, CategoryClothMenViewModel>(
    FragmentCategoryClothMenBinding::inflate
), CategoryClothMenContract.View {
    override val viewModel: CategoryClothMenViewModel by viewModel()

    override fun initView() {
        getCategory()
    }

    override fun getCategory() {
        viewModel.apply {
            getDataById(99)
        }
    }

    override fun showLoading(isVisible: Boolean) {
        getViewBinding().pbLoading.isVisible = isVisible
    }

    override fun observeData() {
        with(viewModel) {
            getDataCategoryResult().observe(viewLifecycleOwner) {
                when(it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        getDataCategory(it.data)
                    }
                    is Resource.Error -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun getDataCategory(data: CategoryResponse?) {
        viewModel.apply {
            with(getViewBinding()) {
                val listAdapter = CategoryAllAdapter{

                }
                listAdapter.submitList(data)
                rvCategory.adapter = listAdapter
            }
        }
    }

}