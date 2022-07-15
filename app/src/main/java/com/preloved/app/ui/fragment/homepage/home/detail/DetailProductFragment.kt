package com.preloved.app.ui.fragment.homepage.home.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import com.preloved.app.databinding.FragmentDetailProductBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailProductFragment : BaseFragment<FragmentDetailProductBinding, DetailProductViewModel>(
    FragmentDetailProductBinding::inflate
), DetailProductContract.View {
    private val args by navArgs<DetailProductFragmentArgs>()
    override val viewModel: DetailProductViewModel by viewModel()

    override fun initView() {
        getDataDetail()
        onClick()
    }

    private fun onClick() {
        getViewBinding().apply {
            ibBack.setOnClickListener {
                when(args.status) {
                    0 -> {
                        findNavController().navigate(R.id.action_detailProductFragment_to_mainFragment)
                    }
                    else -> {
                        findNavController().navigate(R.id.action_detailProductFragment2_to_searchProductFragment)
                    }
                }
            }
        }
    }

    override fun getDataDetail() {
        viewModel.apply {
            getDetailProductById(args.productId)
        }
    }

    override fun observeData() {
        viewModel.apply {
            getDetailProductResult().observe(viewLifecycleOwner) {
                when(it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        getDataProduct(it.data)
                    }
                    is Resource.Error -> {
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun getDataProduct(dataCategory: CategoryDetailResponse?) {
        viewModel.apply {
            with(getViewBinding()) {
                dataCategory?.let {
                    tvTitleItem.text = it.name
                    tvCategoryItem.text = it.categories.toString()
                    tvPriceItem.text = it.basePrice.toString()
                    Glide.with(root)
                        .load(it.imageUrl)
                        .into(ivUserPhoto)
                    tvUsername.text = it.user.fullName
                    tvUserLocation.text = it.user.city
                    tvItemDescription.text = it.description
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), "${args.productId}", Toast.LENGTH_SHORT).show()
    }

}