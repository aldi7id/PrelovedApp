package com.preloved.app.ui.fragment.homepage.home.detail

import android.app.AlertDialog
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.data.network.model.response.bid.get.GetBidResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import com.preloved.app.databinding.FragmentDetailProductBinding
import com.preloved.app.ui.fragment.homepage.home.bid.PopUpBidFragment
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
                findNavController().popBackStack()
//                when(args.status) {
//                    0 -> {
//                        findNavController().navigate(R.id.action_detailProductFragment_to_mainFragment)
//                    }
//                    else -> {
//                        findNavController().navigate(R.id.action_detailProductFragment2_to_searchProductFragment)
//                    }
//                }
            }
        }
    }

    override fun getDataDetail() {
        viewModel.apply {
            getDetailProductById(args.productId)
            getTokenAccess()
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
            getBuyerOrderResult().observe(viewLifecycleOwner) {
                when(it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        getDataOrder(it.data)
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }

    private fun getDataOrder(data: GetBidResponse?) {
        viewModel.apply {
            with(getViewBinding()) {
                data?.size?.let {
                    for (order in 0 until it) {
                        when {
                            data[order].productId == args.productId && data[order].status == "pending" -> {
                                btnBuy.isEnabled = false
                                btnBuy.text = "Menunggu"
                                btnBuy.setBackgroundColor(R.color.grey_shade)
                            }
                            data[order].productId == args.productId && data[order].status == "accepted" -> {
                                btnBuy.visibility = View.GONE
                            }
//                            data[order].productId == args.productId && data[order].status == "decline" -> {
//
//                            }
                        }
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
                    tvCategoryItem.text = it.categories.joinToString{ data ->
                        data.name
                    }
                    tvPriceItem.text = "Rp. ${it.basePrice}"
                    Glide.with(root)
                        .load(it.imageUrl)
                        .into(ivUserPhoto)
                    tvUsername.text = it.user.fullName
                    tvUserLocation.text = it.user.city
                    tvItemDescription.text = it.description
                    btnBuy.setOnClickListener {
                        getTokenAccessResult().observe(viewLifecycleOwner) { token ->
                            when(token.access_token) {
                                DatastoreManager.DEFAULT_ACCESS_TOKEN -> {
                                    AlertDialog.Builder(context)
                                        .setTitle(getString(R.string.warning))
                                        .setMessage(getString(R.string.please_login))
                                        .setPositiveButton(getString(R.string.login)) { dialogP, _ ->
                                            dialogP.dismiss()
                                            when(args.status) {
                                                0 -> {
                                                    findNavController().navigate(R.id.action_detailProductFragment_to_loginFragment)
                                                }
                                                else -> {
                                                    findNavController().navigate(R.id.action_detailProductFragment2_to_loginFragment3)
                                                }
                                            }
                                        }
                                        .setNegativeButton(getString(R.string.later)) { dialogN, _ ->
                                            dialogN.dismiss()
                                        }
                                        .setCancelable(false)
                                        .show()
                                }
                                else -> {
                                    PopUpBidFragment(args.productId).show(parentFragmentManager, "")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}