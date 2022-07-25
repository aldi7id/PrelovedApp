package com.preloved.app.ui.fragment.homepage.account.wishlist

import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.databinding.FragmentWishlistBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WishlistFragment : BaseFragment<FragmentWishlistBinding, WishlistRepository>(
    FragmentWishlistBinding::inflate
), WishlistContract.View {

    override val viewModel: WishlistRepository by viewModel()

    override fun initView() {
        getViewBinding().apply {
            getData()
        }
    }

    override fun getData() {
        viewModel.apply {

        }
    }

    override fun observeData() {
        viewModel.apply {

        }
    }

}