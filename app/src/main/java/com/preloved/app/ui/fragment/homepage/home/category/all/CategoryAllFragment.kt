package com.preloved.app.ui.fragment.homepage.home.category.all

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.databinding.FragmentCategoryAllBinding
import com.preloved.app.ui.fragment.MainFragmentDirections
import com.preloved.app.ui.fragment.homepage.home.HomeFragmentDirections
import com.preloved.app.ui.fragment.homepage.home.category.CategoryAllAdapter
import com.preloved.app.ui.fragment.homepage.home.search.SearchProductFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryAllFragment : BaseFragment<FragmentCategoryAllBinding, CategoryAllViewModel>(
    FragmentCategoryAllBinding::inflate
), CategoryAllContract.View {
    override val viewModel: CategoryAllViewModel by viewModel()

    override fun initView() {
        getDataCategory()
        onClick()
    }

    private fun onClick() {
        getViewBinding().apply {

        }
    }

    override fun getDataCategory() {
        viewModel.apply {
            getDataAllCategory()
        }
    }

    override fun showLoading(isVisible: Boolean) {
        getViewBinding().pbLoading.isVisible = isVisible
    }

    override fun observeData() {
        viewModel.apply {
            getAllCategoryResult().observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        getData(it.data)
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        showError(true, it.message)
                    }
                }
            }
        }
    }

    private fun getData(data: CategoryResponse?) {
        getViewBinding().apply {
            val listCategoryAdapter = CategoryAllAdapter {
                val passData = MainFragmentDirections.actionMainFragmentToDetailProductFragment(
                    productId = it.id
                )
                findNavController().navigate(passData)
            }
            listCategoryAdapter.submitList(data?.filter {it.status == "available"}?.sortedByDescending { it.id } )
            rvListCategory.adapter = listCategoryAdapter
        }
    }

}