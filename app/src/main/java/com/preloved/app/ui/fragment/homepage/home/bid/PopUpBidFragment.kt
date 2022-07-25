package com.preloved.app.ui.fragment.homepage.home.bid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.request.bid.BidRequest
import com.preloved.app.data.network.model.response.bid.post.PostBidResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import com.preloved.app.databinding.FragmentPopUpBidBinding
import com.preloved.app.ui.currency
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopUpBidFragment(
    private val productId: Int
): BottomSheetDialogFragment() {
    private var bind: FragmentPopUpBidBinding? = null
    private val binding get() = bind!!
    private val viewModel : PopUpBidViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentPopUpBidBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onClick()
        getDataProductOrder()
        observerData()
    }

    private fun getDataProductOrder() {
        viewModel.apply {
            getDetailProductById(productId)
        }
    }

    private fun onClick() {
        binding.apply {
            btnTawar.setOnClickListener {
                when {
                    checkValue() -> {
                        viewModel.postBidProductOrderById(
                            BidRequest(
                                productId = productId,
                                bidPrice = etTawaran.text.toString().toInt()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun checkValue(): Boolean {
        binding.apply {
            var isValid = true

            when {
                etTawaran.text.toString().isEmpty() -> {
                    tilTawaran.isErrorEnabled = true
                    tilTawaran.error = "Fill your price"
                    isValid = false
                }
                else -> {
                    tilTawaran.error = null
                }
            }
            return isValid
        }
    }

    private fun observerData() {
        viewModel.apply {
            getDetailProductResult().observe(viewLifecycleOwner) {
                when(it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        getDataProduct(it.data)
                    }
                    is Resource.Error -> {

                    }
                }
            }
            postBidProductOrderByIdResult().observe(viewLifecycleOwner) {
                when(it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        postData(it.data)
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
            }
        }
    }

    private fun getDataProduct(dataCategory: CategoryDetailResponse?) {
        viewModel.apply {
            with(binding) {
                dataCategory?.let {
                    Glide.with(root)
                        .load(it.imageUrl)
                        .into(ivProduct)
                    tvProductName.text = it.name
                    tvProductCategory.text = it.categories.joinToString{ data ->
                        data.name
                    }
                    tvProductPrice.text = currency(it.basePrice)
                }
            }
        }
    }

    private fun postData(data: PostBidResponse?) {
        binding.apply {
            when(data?.status) {
                "pending" -> {
                    Toast.makeText(requireContext(), "Your bid product Success", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
        }
    }

}