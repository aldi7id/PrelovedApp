package com.preloved.app.ui.fragment.homepage.home.search

import android.os.Handler
import android.os.Looper
import android.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
    private val args by navArgs<SearchProductFragmentArgs>()
    private lateinit var searchData: String
    override val viewModel: SearchProductViewModel by viewModel()

    override fun initView() {
        onView()
        onClick()
        getProduct()
    }

    private fun onView() {
        viewModel.apply {
            getDataSearchProduct(args.search.toString())
            searchProduct()
        }
    }

    private fun onClick() {
        getViewBinding().apply {
            ivBack.setOnClickListener {
                findNavController().navigate(R.id.action_searchProductFragment_to_homeFragment)
            }
            svSearchItem.setQuery(args.search, true)
        }
    }

    private fun searchProduct() {
        getViewBinding().svSearchItem.apply {
            setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    searchData = p0.toString()
                    viewModel.getDataSearchProduct(searchData)
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }
            })
        }
    }

    override fun getProduct() {
        getViewBinding().apply {
            swipeRefreshLayout.setOnRefreshListener {
                Handler(Looper.getMainLooper()).postDelayed({
                    viewModel.getDataSearchProduct(searchData)
                    swipeRefreshLayout.isRefreshing = false
                }, 2000)
            }
        }
    }

    override fun showLoading(isVisible: Boolean) {
        super.showLoading(isVisible)
        getViewBinding().pbLoading.isVisible = isVisible
    }

    override fun showContent(isVisible: Boolean) {
        super.showContent(isVisible)
        getViewBinding().rvListCategory.isVisible = isVisible
    }

    override fun observeData() {
        viewModel.apply {
            getSearchProductResult().observe(viewLifecycleOwner) {
                when(it){
                    is Resource.Loading -> {
                        showLoading(true)
                        showContent(false)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        showContent(true)
                        getSearchProduct(it.data)
                    }
                    is Resource.Error -> {
                        showContent(true)
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun getSearchProduct(data: CategoryResponse?) {
        getViewBinding().apply {
            val searchAdapter = CategoryAllAdapter {
                val passData = SearchProductFragmentDirections.actionSearchProductFragmentToDetailProductFragment2(
                    productId = it.id,
                    status = 1
                )
                findNavController().navigate(passData)
            }
            searchAdapter.submitList(data?.filter {it.status == "available"})
            rvListCategory.adapter = searchAdapter
        }
    }
}