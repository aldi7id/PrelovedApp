package com.preloved.app.ui.fragment.homepage.sale

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.data.network.model.response.SellerOrderResponse
import com.preloved.app.data.network.model.response.SellerProductResponseItem
import com.preloved.app.databinding.FragmentSaleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SaleFragment : BaseFragment<FragmentSaleBinding, SaleViewModel>
    (FragmentSaleBinding::inflate), SaleContract.View  {
    override val viewModel: SaleViewModel by viewModel()
    private val bundle = Bundle()
    private var token = ""
    private val bundleEdit = Bundle()
    private val bundlePenawar = Bundle()
    private var status = "accepted"

    companion object {
        const val USER_TOKEN = "UserToken"
        const val USER_NAME = "UserName"
        const val USER_CITY = "UserCity"
        const val USER_IMAGE = "UserCity"
        const val ORDER_ID = "OrderId"
        const val ORDER_STATUS = "OrderStatus"
        const val PRODUCT_IMAGE = "ProductImage"
        const val PRODUCT_NAME = "ProductName"
        const val PRODUCT_CATEGORY = "productCategory"
        const val PRODUCT_DESCRIPTION = "productDescription"
        const val PRODUCT_PRICE = "ProductPrice"
        const val PRODUCT_BID = "ProductBid"
        const val PRODUCT_BID_DATE = "ProductBidDate"
        const val PRODUCT_ID = "productId"
    }

    override fun initView() {
        viewModel.userSession()
        getViewBinding().btnEdit.setOnClickListener {
            findNavController().navigate(R.id.action_saleFragment_to_editProfileFragment, bundle)
        }
        setOnClickListeners()
    }

    override fun setOnClickListeners() {
        getViewBinding().btnTerjual.setOnClickListener {
            getViewBinding().apply {
                rvProduct.visibility = View.GONE
                rvDiminati.visibility = View.GONE
                btnProduk.setBackgroundColor(Color.parseColor("#EC698F"))
                btnDiminati.setBackgroundColor(Color.parseColor("#EC698F"))
                btnTerjual.setBackgroundColor(Color.parseColor("#06283D"))
            }
            viewModel.getSellerProductOrderAccepted(token,status)
            viewModel.getSellerProductOrderAcceptedResult().observe(viewLifecycleOwner){
                when (it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        val saleOrderAcceptedAdapter = SaleAcceptedAdapter()
                        saleOrderAcceptedAdapter.submitData(it.data)
                        getViewBinding().rvTerjual.adapter = saleOrderAcceptedAdapter
                        getViewBinding().rvTerjual.visibility = View.VISIBLE
                        if (it.data?.size == 0) {
                            getViewBinding().lottieEmpty.visibility = View.VISIBLE
                    }

                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
        getViewBinding().btnDiminati.setOnClickListener {
            getViewBinding().apply {
                rvProduct.visibility = View.GONE
                rvTerjual.visibility = View.GONE
                btnProduk.setBackgroundColor(Color.parseColor("#EC698F"))
                btnDiminati.setBackgroundColor(Color.parseColor("#06283D"))
                btnTerjual.setBackgroundColor(Color.parseColor("#EC698F"))
            }
            viewModel.getSellerProductOrder(token)
            viewModel.getSellerProductOrderResult().observe(viewLifecycleOwner){

                when (it) {
                    is Resource.Loading -> {
                        getViewBinding().apply {
                            showLoading(true)
                            rvProduct.visibility = View.GONE
                            rvDiminati.visibility = View.GONE
                            rvTerjual.visibility = View.GONE
                            lottieEmpty.visibility = View.GONE
                        }
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        if (it.data != null) {
                            val sellerOrderAdapter =
                                SaleOrderAdapter(object : SaleOrderAdapter.OnClickListener {
                                    override fun onClickItem(data: SellerOrderResponse) {
                                        bundlePenawar.putString(
                                            USER_NAME,
                                            data.buyerInformation.fullName
                                        )
                                        bundlePenawar.putString(
                                            USER_CITY,
                                            data.buyerInformation.city.toString()
                                        )
                                        bundlePenawar.putInt(ORDER_ID, data.id)
                                        bundlePenawar.putString(ORDER_STATUS, data.status)
                                        bundlePenawar.putString(PRODUCT_NAME, data.product.name)
                                        bundlePenawar.putString(
                                            PRODUCT_PRICE,
                                            data.product.basePrice.toString()
                                        )
                                        bundlePenawar.putString(
                                            PRODUCT_BID,
                                            data.price.toString()
                                        )
                                        bundlePenawar.putString(
                                            PRODUCT_IMAGE,
                                            data.product.imageUrl
                                        )
                                        bundlePenawar.putString(
                                            PRODUCT_BID_DATE,
                                            data.createdAt
                                        )
//                                        findNavController().navigate(
//                                            R.id.action_daftarJualFragment_to_infoPenawarFragment,
//                                            bundlePenawar
//                                        )
                                    }
                                })
                            sellerOrderAdapter.submitData(it.data)
                            getViewBinding().rvDiminati.adapter = sellerOrderAdapter
                            getViewBinding().rvDiminati.visibility = View.VISIBLE

                        }
                        if (it.data?.size == 0) {
                            getViewBinding().lottieEmpty.visibility = View.VISIBLE
                        }
                        //binding.pbLoading.visibility = View.GONE
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
        getViewBinding().btnProduk.setOnClickListener {
            getViewBinding().apply {
                rvDiminati.visibility = View.GONE
                rvTerjual.visibility = View.GONE
                btnProduk.setBackgroundColor(Color.parseColor("#06283D"))
                btnDiminati.setBackgroundColor(Color.parseColor("#EC698F"))
                btnTerjual.setBackgroundColor(Color.parseColor("#EC698F"))
            }
            viewModel.getSellerProduct(token)
            viewModel.getSellerProductResult().observe(viewLifecycleOwner){
                when (it) {
                    is Resource.Loading -> {
                        getViewBinding().apply {
                            showLoading(true)
                        }
                    }
                    is Resource.Success -> {
                        if (it.data != null) {
                            val saleProductAdapter =
                                SaleProductAdapter(object  : SaleProductAdapter.OnclickListener{
                                    var listCategory = ""
                                    override fun onClickItem(data: SellerProductResponseItem) {
                                        bundleEdit.apply {
                                            putInt(PRODUCT_ID, data.id)
                                            putString(PRODUCT_NAME, data.name)
                                            putInt(PRODUCT_PRICE, data.basePrice)
                                            for (kategory in data.categories){
                                                listCategory += ",${kategory.name}"
                                            }
                                            putString(PRODUCT_CATEGORY, listCategory.drop(2))
                                            putString(PRODUCT_DESCRIPTION, data.description)
                                            putString(PRODUCT_IMAGE, data.imageUrl)
                                        }
                                        //EditProductNavigasi
                                    }
                                })
                            saleProductAdapter.submitData(it.data)
                            getViewBinding().rvProduct.adapter = saleProductAdapter
                            getViewBinding().rvProduct.visibility = View.VISIBLE
                        }
                        if (it.data?.size == 0){
                            getViewBinding().lottieEmpty.visibility = View.VISIBLE
                        }
                        getViewBinding().buttonGrup.visibility = View.VISIBLE
                        //pbloading
                        getViewBinding().btnProduk.setBackgroundColor(Color.parseColor("#06283D"))
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    override fun showLoading(isVisible: Boolean) {
        super.showLoading(isVisible)
        getViewBinding().pbLoadingUser.isVisible = isVisible
        getViewBinding().pbLoading.isVisible = isVisible
    }

    override fun observeData() {
        viewModel.userSessionResult().observe(viewLifecycleOwner) {
            if(it.access_token == DatastoreManager.DEFAULT_ACCESS_TOKEN){
                AlertDialog.Builder(context)
                    .setTitle("Warning")
                    .setMessage("Kamu Belum Login Nih")
                    .setPositiveButton("Login") { dialogP, _ ->
                        //ToLogin Fragment
                        findNavController().navigate(R.id.action_saleFragment_to_loginFragment3)
                        dialogP.dismiss()
                    }
                    .setNegativeButton("Tidak") { dialogN, _ ->
                        //ToHomeFragment
                        findNavController().navigate(R.id.homeFragment)
                        dialogN.dismiss()
                    }
                    .setCancelable(false)
                    .show()
                //viewModel.checkLogin().removeObserver(viewLifecycleOwner)
            } else {
                bundle.putString(USER_TOKEN,it.access_token)
                viewModel.getUserData(it.access_token)
                token = it.access_token
            }
            viewModel.getUserDataResult().observe(viewLifecycleOwner){
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        if(it.data != null) {
                            if(it.data.imageUrl != null ) {
                                Glide.with(requireContext())
                                    .load(it.data.imageUrl.toString())
                                    .placeholder(R.drawable.ic_profile)
                                    .transform(CenterCrop(), RoundedCorners(12))
                                    .into(getViewBinding().ivAvatarPenjual)
                            }
                            getViewBinding().apply {
                                tvNamaPenjual.setText(it.data.fullName)
                                tvKotaPenjual.setText(it.data.city)
                            }
                        }
                    }
                    is Resource.Error -> {

                    }
                }
            }
            viewModel.getSellerProduct(token)
            getViewBinding().apply {
                btnProduk.setBackgroundColor(Color.parseColor("#06283D"))
            }
            viewModel.getSellerProductResult().observe(viewLifecycleOwner){
                when (it) {
                    is Resource.Loading -> {
                        getViewBinding().apply {
                            //VISIBLE
                        }
                    }
                    is Resource.Success -> {
                        showLoading(false)
                    if (it.data != null) {
                        val saleProductAdapter =
                            SaleProductAdapter(object  : SaleProductAdapter.OnclickListener{
                                var listCategory = ""
                                override fun onClickItem(data: SellerProductResponseItem) {
                                   bundleEdit.apply {
                                       putInt(PRODUCT_ID, data.id)
                                       putString(PRODUCT_NAME, data.name)
                                       putInt(PRODUCT_PRICE, data.basePrice)
                                       for (kategory in data.categories){
                                           listCategory += ",${kategory.name}"
                                       }
                                       putString(PRODUCT_CATEGORY, listCategory.drop(2))
                                       putString(PRODUCT_DESCRIPTION, data.description)
                                       putString(PRODUCT_IMAGE, data.imageUrl)
                                   }
                                    //EditProductNavigasi
                                }
                            })
                        saleProductAdapter.submitData(it.data)
                        getViewBinding().rvProduct.adapter = saleProductAdapter
                        getViewBinding().rvProduct.visibility = View.VISIBLE
                    }
                        if (it.data?.size == 0){
                            getViewBinding().lottieEmpty.visibility = View.VISIBLE
                        }
                        getViewBinding().buttonGrup.visibility = View.VISIBLE
                        //pbloading
                        getViewBinding().btnProduk.setBackgroundColor(Color.parseColor("#06283D"))
                    }
                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}