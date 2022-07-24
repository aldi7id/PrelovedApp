package com.preloved.app.ui.fragment.homepage.home.bid.edit

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.preloved.app.R
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.data.network.model.request.bid.BidRequest
import com.preloved.app.data.network.model.response.BuyerOrderEditResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import com.preloved.app.databinding.FragmentPopUpBidBinding
import com.preloved.app.ui.currency
import com.preloved.app.ui.fragment.homepage.home.bid.PopUpBidViewModel
import com.preloved.app.ui.striketroughtText
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopUpBidEditFragment(private val productId: Int, private val orderId: Int, private val lastBid: Int) : BottomSheetDialogFragment()  {
    private var bind: FragmentPopUpBidBinding? = null
    private val binding get() = bind!!
    private val viewModel : PopUpBidEditViewModel by viewModel()
    private var token = ""

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
        viewModel.getTokenAccess()

    }

    private fun observerData() {
        viewModel.apply {
            getDetailProductResult().observe(viewLifecycleOwner) {
                when (it) {
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

            getTokenAccessResult().observe(viewLifecycleOwner) {
                if(it.access_token == DatastoreManager.DEFAULT_ACCESS_TOKEN) {
                    AlertDialog.Builder(context)
                        .setTitle(getString(R.string.warning))
                        .setMessage(getString(R.string.please_login))
                        .setPositiveButton(getString(R.string.login)) { dialogP, _ ->
                            //ToLogin Fragment
                            dialogP.dismiss()
                            viewModel.getTokenAccessResult().removeObservers(viewLifecycleOwner)

                        }
                        .setNegativeButton(getString(R.string.later)) { dialogN, _ ->
                            //ToHomeFragment
                            dialogN.dismiss()
                            findNavController().navigate(R.id.homeFragment)

                        }
                        .setCancelable(false)
                        .show()
                } else {
                    token = it.access_token
                }
                viewModel.getTokenAccessResult().removeObservers(viewLifecycleOwner)
            }
        }
    }

    private fun postData(data: BuyerOrderEditResponse?) {
        binding.apply {
            when(data?.status) {
                "pending" -> {
                    Toast.makeText(requireContext(), "Your bid product Success", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
        }
    }

    private fun getDataProduct(data: CategoryDetailResponse?) {
        viewModel.apply {
            with(binding) {
                data?.let {
                    Glide.with(root)
                        .load(it.imageUrl)
                        .into(ivProduct)
                    tvProductName.text = it.name
                    tvProductCategory.text = it.categories.joinToString{ data ->
                        data.name
                    }
                    tvProductPrice.apply {
                        text = striketroughtText(this, currency(it.basePrice))
                    }
                    tvBidPrice.apply {
                        visibility = View.VISIBLE
                        text = "Your Bid: ".plus(currency(lastBid))
                    }
                    btnTawar.text = "Update Bid"
                }
            }
        }
    }

    private fun onClick() {
        binding.apply {
            btnTawar.setOnClickListener {
                when {
                    checkValue() -> {
                        viewModel.postBidProductOrderById(
                            token = token,
                            id = orderId,
                            bid_price = etTawaran.text.toString().toInt()
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

    private fun getDataProductOrder() {
        viewModel.apply {
            getDetailProductById(productId)
        }
    }

}