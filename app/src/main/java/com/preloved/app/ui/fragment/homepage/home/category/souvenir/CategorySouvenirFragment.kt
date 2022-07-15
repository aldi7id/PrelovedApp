package com.preloved.app.ui.fragment.homepage.home.category.souvenir

import androidx.core.view.isVisible
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.databinding.FragmentCategorySouvenirBinding
import com.preloved.app.databinding.FragmentCategoryVoucherBinding
import com.preloved.app.ui.fragment.homepage.home.category.CategoryAllAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategorySouvenirFragment : BaseFragment<FragmentCategorySouvenirBinding, CategorySouvenirViewModel>(
    FragmentCategorySouvenirBinding::inflate
), CategorySouvenirContract.View {
    override val viewModel: CategorySouvenirViewModel by viewModel()

    override fun initView() {
        getCategory()
    }

    override fun getCategory() {
        viewModel.apply {
            getDataById(118)
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