package com.preloved.app.ui.fragment.homepage.home.search

import android.widget.SearchView.OnQueryTextListener
import androidx.navigation.fragment.findNavController
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.databinding.FragmentSearchProductBinding
import com.preloved.app.ui.fragment.homepage.home.category.CategoryAllAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchProductFragment : BaseFragment<FragmentSearchProductBinding, SearchProductViewModel>(
    FragmentSearchProductBinding::inflate
), SearchProductContract.View {
    override val viewModel: SearchProductViewModel by viewModel()

    override fun initView() {
        getProduct()
        onClick()
    }

    private fun onClick() {
        getViewBinding().apply {
            ivBack.setOnClickListener {
                findNavController().navigate(R.id.action_searchProductFragment_to_homeFragment)
            }
        }
    }

    override fun getProduct() {
        viewModel.apply {
            with(getViewBinding()) {
                with(svSearchItem) {
                    setOnQueryTextListener(object : OnQueryTextListener {
                        override fun onQueryTextSubmit(p0: String?): Boolean {
                            getDataSearchProduct(p0.toString())
                            return false
                        }

                        override fun onQueryTextChange(p0: String?): Boolean {
                            return false
                        }
                    })
                }

            }
        }
    }

    override fun observeData() {
        viewModel.apply {
            getSearchProductResult().observe(viewLifecycleOwner) {
                when(it){
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        getSearchProduct(it.data)
                    }
                    is Resource.Error -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun getSearchProduct(data: CategoryResponse?) {
        getViewBinding().apply {
            val searchAdapter = CategoryAllAdapter {
                val passData = SearchProductFragmentDirections.actionSearchProductFragmentToDetailProductFragment(
                    productId = it.id
                )
                findNavController().navigate(passData)
            }
            searchAdapter.submitList(data)
            rvListCategory.adapter = searchAdapter
        }
    }
}