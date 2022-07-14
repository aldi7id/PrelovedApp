package com.preloved.app.ui.fragment.homepage.home.category.bookandpen

import androidx.core.view.isVisible
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.databinding.FragmentCategoryBookandpenBinding
import com.preloved.app.databinding.FragmentCategoryHealthyBinding
import com.preloved.app.ui.fragment.homepage.home.category.CategoryAllAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryBookAndPenFragment : BaseFragment<FragmentCategoryBookandpenBinding, CategoryBookAndPenViewModel>(
    FragmentCategoryBookandpenBinding::inflate
), CategoryBookAndPenContract.View {
    override val viewModel: CategoryBookAndPenViewModel by viewModel()

    override fun initView() {
        getCategory()
    }

    override fun getCategory() {
        viewModel.apply {
            getDataById(116)
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