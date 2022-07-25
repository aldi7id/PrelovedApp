package com.preloved.app.ui.fragment.homepage.account.wishlist

import androidx.navigation.fragment.findNavController
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.data.network.model.response.whislist.GetWishlistResponse
import com.preloved.app.databinding.FragmentWishlistBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WishlistFragment : BaseFragment<FragmentWishlistBinding, WishlistViewModel>(
    FragmentWishlistBinding::inflate
), WishlistContract.View {

    override val viewModel: WishlistViewModel by viewModel()

    override fun initView() {
        getViewBinding().apply {
            getData()
            onClick()
        }
    }

    private fun onClick() {
        getViewBinding().apply {
            ibBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun getData() {
        viewModel.apply {
            getTokenAccess()
        }
    }

    override fun observeData() {
        viewModel.apply {
            getTokenAccessResult().observe(viewLifecycleOwner) {
                getDataAction(it)
            }
            getWishlistProductResult().observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        getWishlist(it.data)
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        showError(true, it.message)
                    }
                }
            }
        }
    }

    private fun getWishlist(data: GetWishlistResponse?) {
        getViewBinding().apply {
            val listCategoryAdapter = WishlistAdapter {
                val passData = WishlistFragmentDirections.actionWishlistFragmentToDetailProductFragment2(
                    productId = it.id
                )
                findNavController().navigate(passData)
            }
            listCategoryAdapter.submitList(data)
            rvListCategory.adapter = listCategoryAdapter
        }
    }

    private fun getDataAction(data: DatastorePreferences?) {
        viewModel.apply {
            data?.let {
                getWishlistProduct(it.access_token)
            }
        }
    }
}