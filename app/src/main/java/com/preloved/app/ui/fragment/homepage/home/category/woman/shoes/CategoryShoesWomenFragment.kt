package com.preloved.app.ui.fragment.homepage.home.category.woman.shoes

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.databinding.FragmentCategoryClothWomenBinding
import com.preloved.app.databinding.FragmentCategoryShoesWomenBinding
import com.preloved.app.ui.fragment.MainFragmentDirections
import com.preloved.app.ui.fragment.homepage.home.category.CategoryAllAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoryShoesWomenFragment : BaseFragment<FragmentCategoryShoesWomenBinding, CategoryShoesWomenViewModel>(
   FragmentCategoryShoesWomenBinding::inflate
), CategoryShoesWomenContract.View {
    override val viewModel: CategoryShoesWomenViewModel by viewModel()

    override fun initView() {
        getCategory()
    }

    override fun getCategory() {
        viewModel.apply {
            getDataById(17)
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
                    val passData = MainFragmentDirections.actionMainFragmentToDetailProductFragment(
                        productId = it.id
                    )
                    findNavController().navigate(passData)
                }
                listAdapter.submitList(data?.filter {it.status == "available"}?.sortedByDescending { it.id })
                rvCategory.adapter = listAdapter
            }
        }
    }

}