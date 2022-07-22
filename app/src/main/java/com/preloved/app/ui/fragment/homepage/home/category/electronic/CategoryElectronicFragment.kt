package com.preloved.app.ui.fragment.homepage.home.category.electronic

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.databinding.FragmentCategoryElectronicBinding
import com.preloved.app.ui.fragment.MainFragmentDirections
import com.preloved.app.ui.fragment.homepage.home.category.CategoryAllAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryElectronicFragment : BaseFragment<FragmentCategoryElectronicBinding, CategoryElectronicViewModel>(
    FragmentCategoryElectronicBinding::inflate
), CategoryElectronicContract.View {
    override val viewModel: CategoryElectronicViewModel by viewModel()

    override fun initView() {
        getCategoryElectronic()
    }

    override fun getCategoryElectronic() {
        viewModel.apply {
            getDataElectronicById(96)
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
                val listElectronicAdapter = CategoryAllAdapter {
                    val passData = MainFragmentDirections.actionMainFragmentToDetailProductFragment(
                        productId = it.id
                    )
                    findNavController().navigate(passData)
                }
                listElectronicAdapter.submitList(data?.filter {it.status == "available"})
                rvCategory.adapter = listElectronicAdapter
            }
        }
    }

}