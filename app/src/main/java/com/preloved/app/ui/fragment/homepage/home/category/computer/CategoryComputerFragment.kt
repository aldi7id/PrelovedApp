package com.preloved.app.ui.fragment.homepage.home.category.computer

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.databinding.FragmentCategoryComputerBinding
import com.preloved.app.ui.fragment.MainFragmentDirections
import com.preloved.app.ui.fragment.homepage.home.category.CategoryAllAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryComputerFragment : BaseFragment<FragmentCategoryComputerBinding, CategoryComputerViewModel>(
    FragmentCategoryComputerBinding::inflate
), CategoryComputerContract.View {
    override val viewModel: CategoryComputerViewModel by viewModel()

    override fun initView() {
        getCategoryElectronic()
    }

    override fun getCategoryElectronic() {
        viewModel.apply {
            getDataComputerById(97)
        }
    }

    override fun showLoading(isVisible: Boolean) {
        getViewBinding().pbLoading.isVisible = isVisible
    }

    override fun observeData() {
        with(viewModel) {
            getDataElectronicResult().observe(viewLifecycleOwner) {
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
                val listComputerAdapter = CategoryAllAdapter {
                    val passData = MainFragmentDirections.actionMainFragmentToDetailProductFragment(
                        productId = it.id
                    )
                    findNavController().navigate(passData)
                }
                listComputerAdapter.submitList(data?.filter {it.status == "available"})
                rvCategory.adapter = listComputerAdapter
            }
        }
    }

}