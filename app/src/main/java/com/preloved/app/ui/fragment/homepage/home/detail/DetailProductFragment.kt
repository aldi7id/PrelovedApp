package com.preloved.app.ui.fragment.homepage.home.detail

import MyOrderFragment.Companion.USER_ORDER
import android.app.ActionBar
import android.app.AlertDialog
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.data.network.model.BuyerOrderResponse
import com.preloved.app.data.network.model.response.bid.get.GetBidResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import com.preloved.app.databinding.FragmentDetailProductBinding
import com.preloved.app.ui.currency
import com.preloved.app.ui.fragment.homepage.home.bid.PopUpBidFragment
import com.preloved.app.ui.fragment.homepage.home.bid.edit.PopUpBidEditFragment
import com.preloved.app.ui.fragment.homepage.sale.SaleFragment
import com.preloved.app.ui.fragment.homepage.sell.SellFragment
import com.preloved.app.ui.listCategory
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class DetailProductFragment : BaseFragment<FragmentDetailProductBinding, DetailProductViewModel>(
    FragmentDetailProductBinding::inflate
), DetailProductContract.View {

    private val args by navArgs<DetailProductFragmentArgs>()
    private var token = ""
    private var idOrders = ""
    private var lastBid = ""


    override val viewModel: DetailProductViewModel by viewModel()

    override fun initView() {
        getDataDetail()
        onClick()
    }

    override fun showLoading(isVisible: Boolean) {
        super.showLoading(isVisible)
        getViewBinding().pbLoading.isVisible = isVisible
    }

    private fun onClick() {
        getViewBinding().apply {
            ibBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnDelete.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle(getString(R.string.warning))
                    .setMessage("Are You Sure Want Delete This Order?")
                    .setPositiveButton(getString(R.string.sure)) { dialogP, _ ->
                        viewModel.deleteBuyerOrder(
                            token = token,
                            id  = idOrders.toInt()
                        )
                        dialogP.dismiss()
                    }
                    .setNegativeButton(getString(R.string.cancel)) { dialogN, _ ->
                        //ToHomeFragment
                        dialogN.dismiss()
                    }
                    .setCancelable(false)
                    .show()
            }
            btnEdit.setOnClickListener {
                PopUpBidEditFragment(args.productId, idOrders.toInt(), lastBid.toInt()).show(parentFragmentManager, "")
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
            getTokenAccessResult().observe(viewLifecycleOwner){
                if(it.access_token == DatastoreManager.DEFAULT_ACCESS_TOKEN) {
                    AlertDialog.Builder(context)
                        .setTitle(getString(R.string.warning))
                        .setMessage(getString(R.string.please_login))
                        .setPositiveButton(getString(R.string.login)) { dialogP, _ ->
                            //ToLogin Fragment
                            dialogP.dismiss()
                            when(args.status) {
                                0 -> {
                                    findNavController().navigate(R.id.action_detailProductFragment_to_loginFragment)
                                }
                                else -> {
                                    findNavController().navigate(R.id.action_detailProductFragment2_to_loginFragment3)
                                }
                            }
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
                    viewModel.getBuyerOrder(it.access_token)
                    //bundle.putString(SellFragment.USER_TOKEN,it.access_token)
                }
                viewModel.getTokenAccessResult().removeObservers(viewLifecycleOwner)
            }
            deleteBuyerOrderResult().observe(viewLifecycleOwner) {
                when(it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        showToastSuccess()
                        findNavController().popBackStack()
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
                                btnBuy.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey_shade))
                                btnGroup.visibility = View.VISIBLE
                                idOrders = data[order].id.toString()
                                lastBid = data[order].price.toString()
                                Log.d("HAYO", idOrders)
                                Log.d("HAYO", lastBid)
                            }
                            data[order].productId == args.productId && data[order].status == "accepted" -> {
                                btnBuy.visibility = View.GONE
                                AlertDialog.Builder(context)
                                    .setTitle(getString(R.string.congratulations))
                                    .setMessage(getString(R.string.your_bid))
                                    .setPositiveButton(getString(R.string.OK)) { dialogP, _ ->
                                        dialogP.dismiss()

                                    }
                                    .show()
                            }
                            data[order].productId == args.productId && data[order].status == "declined" -> {
                                AlertDialog.Builder(context)
                                    .setTitle(getString(R.string.warning))
                                    .setMessage(getString(R.string.Unfortunately))
                                    .setPositiveButton(getString(R.string.OK)) { dialogP, _ ->
                                        dialogP.dismiss()
                                    }
                                    .show()
                                btnBuy.isEnabled = false
                                btnBuy.text = "Declined"
                                btnBuy.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.grey_shade))
                                btnGroup.visibility = View.VISIBLE
                                idOrders = data[order].id.toString()
                                lastBid = data[order].price.toString()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showToastSuccess() {
        val snackBarView =
            Snackbar.make(getViewBinding().root, "Your Order Success Delete", Snackbar.LENGTH_LONG)
        val layoutParams = ActionBar.LayoutParams(snackBarView.view.layoutParams)
        snackBarView.setAction(" ") {
            snackBarView.dismiss()
        }
        val textView =
            snackBarView.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_close, 0)
        textView.compoundDrawablePadding = 16
        layoutParams.gravity = Gravity.TOP
        layoutParams.setMargins(32, 150, 32, 0)
        snackBarView.view.setPadding(24, 16, 0, 16)
        snackBarView.view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
        snackBarView.view.layoutParams = layoutParams
        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()
    }
    private fun getDataProduct(dataCategory: CategoryDetailResponse?) {
        viewModel.apply {
            with(getViewBinding()) {
                dataCategory?.let {
                    Glide.with(root)
                        .load(it.imageUrl)
                        .centerCrop()
                        .into(ivItem)
                    tvTitleItem.text = it.name
                    tvCategoryItem.text = it.categories.joinToString{ data ->
                        data.name
                    }
                    tvPriceItem.text = currency(it.basePrice)
                    Glide.with(root)
                        .load(it.user.imageUrl)
                        .centerCrop()
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