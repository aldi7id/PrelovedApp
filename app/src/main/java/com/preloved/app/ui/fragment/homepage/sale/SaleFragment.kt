package com.preloved.app.ui.fragment.homepage.sale

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
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
import com.preloved.app.data.network.model.HistoryResponseItem
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
    private var statusProduct = "sold"

    companion object {
        const val USER_TOKEN = "UserToken"
        const val USER_NAME = "UserName"
        const val USER_CITY = "UserCity"
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
        getViewBinding().btnHistory.setOnClickListener {
            getViewBinding().apply {
                addProductCard.visibility = View.GONE
                rvProduct.visibility = View.GONE
                rvDiminati.visibility = View.GONE
                rvTerjual.visibility = View.GONE
                btnProduk.setBackgroundColor(Color.parseColor("#EC698F"))
                btnDiminati.setBackgroundColor(Color.parseColor("#EC698F"))
                btnTerjual.setBackgroundColor(Color.parseColor("#EC698F"))
                btnHistory.setBackgroundColor(Color.parseColor("#06283D"))
                viewModel.getHistory(token)
                viewModel.getHistoryResult().observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading(true)
                        }
                        is Resource.Success -> {
                            getViewBinding().apply {
                                rvProduct.visibility = View.GONE
                                rvTerjual.visibility = View.GONE
                                rvDiminati.visibility = View.GONE
                            }
                            showLoading(false)
                            val saleHistoryAdapter = SaleHistoryAdapter(object : SaleHistoryAdapter.OnClickListener{
                                override fun onClickItem(data: HistoryResponseItem) {
                                    val passData = SaleFragmentDirections.actionSaleFragmentToDetailProductFragment2(
                                        productId = data.productId,
                                        status = 1
                                    )
                                    findNavController().navigate(
                                        passData
                                    )
                                }
                            }

                            )
                            val sorted = it.data?.sortedByDescending { it.id }?.filter { it.product != null && it.status == "accepted" && it.product.status == "sold"}
                            saleHistoryAdapter.submitData(sorted)
                            getViewBinding().rvHistory.adapter = saleHistoryAdapter
                            getViewBinding().rvHistory.visibility = View.VISIBLE
                            if(it.data?.size == 0 || sorted?.size == 0){
                                getViewBinding().apply {
                                    lottieEmpty.visibility = View.VISIBLE
                                    tvLottieEmpty.visibility = View.VISIBLE
                                    tvLottieEmpty.text = "No History Show"
                                }
                            } else {
                                lottieEmpty.visibility = View.GONE
                                tvLottieEmpty.visibility = View.GONE
                            }

                        }
                        is Resource.Error -> {

                        }
                    }
                }
            }
        }
        getViewBinding().btnTerjual.setOnClickListener {
            getViewBinding().apply {
                addProductCard.visibility = View.GONE
                rvProduct.visibility = View.GONE
                rvDiminati.visibility = View.GONE
                btnProduk.setBackgroundColor(Color.parseColor("#EC698F"))
                btnDiminati.setBackgroundColor(Color.parseColor("#EC698F"))
                btnTerjual.setBackgroundColor(Color.parseColor("#06283D"))
                btnHistory.setBackgroundColor(Color.parseColor("#EC698F"))
            }
            viewModel.getSellerProductOrder(token)
            viewModel.getSellerProductOrderResult().observe(viewLifecycleOwner){
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        getViewBinding().apply {
                            rvProduct.visibility = View.GONE
                            rvHistory.visibility = View.GONE
                            rvDiminati.visibility = View.GONE
                        }
                        showLoading(false)
                        val saleOrderAcceptedAdapter = SaleAcceptedAdapter(object : SaleAcceptedAdapter.OnClickListener{
                            override fun onClickItem(data: SellerOrderResponse) {
                                bundlePenawar.putInt(ORDER_ID, data.id)
                                val passdata = SaleFragmentDirections.actionSaleFragmentToBuyerInfoFragment(
                                    productId = data.productId,
                                    orderId = data.id
                                )
                                findNavController().navigate(passdata)
                            }
                        }

                        )
                        val sortedSell = it.data?.sortedByDescending { it.id }?.filter { it.status == "accepted" }
                        saleOrderAcceptedAdapter.submitData(sortedSell)
                        getViewBinding().rvTerjual.adapter = saleOrderAcceptedAdapter
                        getViewBinding().rvTerjual.visibility = View.VISIBLE
                        if(it.data?.size == 0 || sortedSell?.size == 0){
                            getViewBinding().apply {
                                lottieEmpty.visibility = View.VISIBLE
                                tvLottieEmpty.visibility = View.VISIBLE
                                tvLottieEmpty.text = getString(R.string.no_product_selled)
                            }
                        } else {
                            getViewBinding().apply {
                                lottieEmpty.visibility = View.GONE
                                tvLottieEmpty.visibility = View.GONE
                            }
                        }

                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
        getViewBinding().btnDiminati.setOnClickListener {
            getViewBinding().apply {
                addProductCard.visibility = View.GONE
                rvProduct.visibility = View.GONE
                rvTerjual.visibility = View.GONE
                rvHistory.visibility = View.GONE
                btnHistory.setBackgroundColor(Color.parseColor("#EC698F"))
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
                            tvLottieEmpty.visibility = View.GONE
                        }
                    }
                    is Resource.Success -> {
                        showLoading(false)

                        if (it.data != null) {
                            val sellerOrderAdapter =
                                SaleOrderAdapter(object : SaleOrderAdapter.OnClickListener {
                                    override fun onClickItem(data: SellerOrderResponse) {
                                        val passdata = SaleFragmentDirections.actionSaleFragmentToBuyerInfoFragment(
                                            orderId = data.id,
                                            productId = data.productId
                                        )
                                        findNavController().navigate(
                                           passdata
                                        )
                                    }
                                })
                            val sorted = it.data.sortedByDescending { it.id }?.filter { it.product.status != "sold" }
                            sellerOrderAdapter.submitData(sorted)
                            getViewBinding().rvDiminati.adapter = sellerOrderAdapter
                            getViewBinding().rvDiminati.visibility = View.VISIBLE
                            if(it.data.size == 0 || sorted?.size == 0){
                                getViewBinding().apply {
                                lottieEmpty.visibility = View.VISIBLE
                                tvLottieEmpty.visibility = View.VISIBLE
                                    tvLottieEmpty.text = getString(R.string.no_product_interested)
                                }
                            } else {
                                getViewBinding().apply {
                                    lottieEmpty.visibility = View.GONE
                                    tvLottieEmpty.visibility = View.GONE
                                }
                            }
                        }


                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
        getViewBinding().btnProduk.setOnClickListener {
            getViewBinding().apply {
                addProductCard.visibility = View.GONE
                rvDiminati.visibility = View.GONE
                rvTerjual.visibility = View.GONE
                rvHistory.visibility = View.GONE
                btnProduk.setBackgroundColor(Color.parseColor("#06283D"))
                btnDiminati.setBackgroundColor(Color.parseColor("#EC698F"))
                btnTerjual.setBackgroundColor(Color.parseColor("#EC698F"))
                btnHistory.setBackgroundColor(Color.parseColor("#EC698F"))
            }
            viewModel.getSellerProduct(token)
            viewModel.getSellerProductResult().observe(viewLifecycleOwner){
                when (it) {
                    is Resource.Loading -> {
                        getViewBinding().apply {
                            showLoading(true)
                            tvLottieEmpty.visibility = View.GONE
                            lottieEmpty.visibility = View.GONE
                        }
                    }
                    is Resource.Success -> {
                        productList(it)
//                        getViewBinding().apply {
//                            rvDiminati.visibility = View.GONE
//                            rvTerjual.visibility = View.GONE
//                            rvHistory.visibility = View.GONE
//                        }
//                        val availableProductSize = it.data?.filter { it.status == "available" }?.size
//                        if (it.data != null) {
//                            val saleProductAdapter =
//                                SaleProductAdapter(object  : SaleProductAdapter.OnclickListener{
//
//                                    override fun onClickItem(data: SellerProductResponseItem) {
//                                        bundleEdit.apply {
//                                            putInt(PRODUCT_ID, data.id)
//                                            putString(USER_CITY, data.location)
//                                        }
//                                        if(data.status == "available") {
//                                            findNavController().navigate(
//                                                R.id.action_saleFragment_to_editProductFragment, bundleEdit
//                                            )
//                                        } else {
//                                            AlertDialog.Builder(context)
//                                                .setTitle(getString(R.string.warning))
//                                                .setMessage(getString(R.string.message_cant_edit_product))
//                                                .setPositiveButton(getString(R.string.OK)) { dialogP, _ ->
//                                                    dialogP.dismiss()
//                                                }
//                                                .setCancelable(false)
//                                                .show()
//                                        }
//                                    }
//
//                                }
//
//                                )
//                            val sorted = it.data.sortedByDescending { it.id }
//                            saleProductAdapter.submitData(sorted)
//                            getViewBinding().rvProduct.adapter = saleProductAdapter
//                            getViewBinding().rvProduct.visibility = View.VISIBLE
//                        }
//                        when(availableProductSize){
//                            0 -> {
//                                getViewBinding().apply {
//                                    lottieEmpty.visibility = View.GONE
//                                    tvLottieEmpty.visibility = View.GONE
//                                    //buttonGrup.visibility = View.GONE
//                                    addProductCard.visibility = View.VISIBLE
//                                    addProductCard.setOnClickListener {
//                                        addProduct()
//                                    }
//                                    tvAddProduct.text = getString(R.string.add_0)
//                                }
//                            }
//                            1 -> {
//                                getViewBinding().apply {
//                                    lottieEmpty.visibility = View.GONE
//                                    tvLottieEmpty.visibility = View.GONE
//                                    addProductCard.visibility = View.VISIBLE
//                                    tvAddProduct.text = getString(R.string.add_1)
//                                    addProductCard.setOnClickListener {
//                                        addProduct()
//                                    }
//                                }
//
//                            }
//                            2 -> {
//                                getViewBinding().apply {
//                                    addProductCard.visibility = View.VISIBLE
//                                    lottieEmpty.visibility = View.GONE
//                                    tvLottieEmpty.visibility = View.GONE
//                                    tvAddProduct.text = getString(R.string.add_2)
//                                    addProductCard.setOnClickListener {
//                                        addProduct()
//                                    }
//                                }
//
//                            }
//                            3 -> {
//                                getViewBinding().apply {
//                                    addProductCard.visibility = View.VISIBLE
//                                    lottieEmpty.visibility = View.GONE
//                                    tvLottieEmpty.visibility = View.GONE
//                                    tvAddProduct.text = getString(R.string.add_3)
//                                    addProductCard.setOnClickListener {
//                                        addProduct()
//                                    }
//                                }
//
//                            }
//                            4 -> {
//                                getViewBinding().apply {
//                                    addProductCard.visibility = View.VISIBLE
//                                    lottieEmpty.visibility = View.GONE
//                                    tvLottieEmpty.visibility = View.GONE
//                                    tvAddProduct.text = getString(R.string.add_4)
//                                    addProductCard.setOnClickListener {
//                                        addProduct()
//                                    }
//                                }
//                            }
//                            5 -> {
//                                getViewBinding().apply {
//                                    addProductCard.visibility = View.VISIBLE
//                                    lottieEmpty.visibility = View.GONE
//                                    tvLottieEmpty.visibility = View.GONE
//                                    tvAddProduct.text = getString(R.string.add_5)
//                                    addProductCard.setOnClickListener {
//                                        AlertDialog.Builder(context)
//                                            .setTitle(getString(R.string.sorry))
//                                            .setMessage(getString(R.string.remove_first))
//                                            .setPositiveButton(getString(R.string.OK)) { positiveButton, _ ->
//                                                positiveButton.dismiss()
//                                            }
//                                            .show()
//                                    }
//                                }
//                            }
//                        }
//                        if(it.data?.size == 0){
//                            getViewBinding().apply {
//                                lottieEmpty.visibility = View.VISIBLE
//                                tvLottieEmpty.visibility = View.VISIBLE
//                                tvLottieEmpty.text = getString(R.string.no_products)
//                            }
//
//                        }else {
//                            getViewBinding().apply {
//                                lottieEmpty.visibility = View.GONE
//                                tvLottieEmpty.visibility = View.GONE
//                            }
//                        }
//                        getViewBinding().btnProduk.setBackgroundColor(Color.parseColor("#06283D"))
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
                    .setTitle(getString(R.string.warning))
                    .setMessage(getString(R.string.please_login))
                    .setPositiveButton(getString(R.string.login)) { dialogP, _ ->
                        //ToLogin Fragment
                        dialogP.dismiss()
                        findNavController().navigate(R.id.action_saleFragment_to_loginFragment3)
                        viewModel.userSessionResult().removeObservers(viewLifecycleOwner)

                    }
                    .setNegativeButton(getString(R.string.later)) { dialogN, _ ->
                        //ToHomeFragment
                        dialogN.dismiss()
                        findNavController().navigate(R.id.homeFragment)

                    }
                    .setCancelable(false)
                    .show()
                viewModel.userSessionResult().removeObservers(viewLifecycleOwner)
            } else {
                bundle.putString(USER_TOKEN,it.access_token)
                viewModel.getUserData(it.access_token)
                token = it.access_token
                viewModel.getSellerProduct(token)
            }
            viewModel.userSessionResult().removeObservers(viewLifecycleOwner)
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
                        if(it.message!!.contains("403")){
                            AlertDialog.Builder(context)
                                .setTitle(getString(R.string.warning))
                                .setMessage(getString(R.string.your_session))
                                .setPositiveButton(getString(R.string.login)) { dialogP, _ ->
                                    dialogP.dismiss()
                                    findNavController().navigate(R.id.action_saleFragment_to_loginFragment3)
                                }
                                .setNegativeButton(getString(R.string.later)) { negativeButton, _ ->
                                    negativeButton.dismiss()
                                    findNavController().popBackStack()
                                }
                                .setCancelable(false)
                                .show()
                        }
                    }
                }
            }
            getViewBinding().apply {
                btnProduk.setBackgroundColor(Color.parseColor("#06283D"))
            }
            viewModel.getSellerProductResult().observe(viewLifecycleOwner){
                when (it) {
                    is Resource.Loading -> {
                        getViewBinding().apply {
                            //VISIBLE
                            addProductCard.visibility = View.GONE
                        }
                    }
                    is Resource.Success -> {
                        productList(it)
//                        showLoading(false)
//                        getViewBinding().apply {
//                            rvDiminati.visibility = View.GONE
//                            rvTerjual.visibility = View.GONE
//                            rvHistory.visibility = View.GONE
//                        }
//                        val availableProductSize = it.data?.filter { it.status == "available" }?.size
//                    if (it.data != null) {
//                        val saleProductAdapter =
//                            SaleProductAdapter(object  : SaleProductAdapter.OnclickListener{
//                                var listCategory = ""
//                                override fun onClickItem(data: SellerProductResponseItem) {
//                                   bundleEdit.apply {
//                                       putInt(PRODUCT_ID, data.id)
//                                       putString(USER_CITY, data.location)
//                                       for (kategori in data.categories){
//                                           listCategory += ", ${kategori.name}"
//                                       }
//                                       putString(PRODUCT_CATEGORY,listCategory.drop(2))
//                                       putString(PRODUCT_DESCRIPTION,data.description)
//                                       putString(PRODUCT_IMAGE,data.imageUrl)
//                                       putInt(PRODUCT_PRICE, data.basePrice)
//                                   }
//                                    if(data.status == "available") {
//                                        findNavController().navigate(
//                                            R.id.action_saleFragment_to_editProductFragment, bundleEdit
//                                        )
//                                    } else {
//                                        AlertDialog.Builder(context)
//                                            .setTitle(getString(R.string.warning))
//                                            .setMessage(getString(R.string.message_cant_edit_product))
//                                            .setPositiveButton(getString(R.string.OK)) { dialogP, _ ->
//                                                dialogP.dismiss()
//                                            }
//                                            .setCancelable(false)
//                                            .show()
//                                    }
//                                }
//                            })
//                        val sorted = it.data.sortedByDescending { it.id }
//                        saleProductAdapter.submitData(sorted)
//                        getViewBinding().rvProduct.adapter = saleProductAdapter
//                        getViewBinding().rvProduct.visibility = View.VISIBLE
//
//                    }
//                        when(availableProductSize){
//                            0 -> {
//                                getViewBinding().apply {
//                                    lottieEmpty.visibility = View.GONE
//                                    tvLottieEmpty.visibility = View.GONE
//                                    //buttonGrup.visibility = View.GONE
//                                    addProductCard.visibility = View.VISIBLE
//                                    addProductCard.setOnClickListener {
//                                        addProduct()
//                                    }
//                                    tvAddProduct.text = getString(R.string.add_0)
//                                }
//                            }
//                            1 -> {
//                                getViewBinding().apply {
//                                    lottieEmpty.visibility = View.GONE
//                                    tvLottieEmpty.visibility = View.GONE
//                                    addProductCard.visibility = View.VISIBLE
//                                    tvAddProduct.text = getString(R.string.add_1)
//                                    addProductCard.setOnClickListener {
//                                        addProduct()
//                                    }
//                                }
//
//                            }
//                            2 -> {
//                                getViewBinding().apply {
//                                    addProductCard.visibility = View.VISIBLE
//                                    lottieEmpty.visibility = View.GONE
//                                    tvLottieEmpty.visibility = View.GONE
//                                    tvAddProduct.text = getString(R.string.add_2)
//                                    addProductCard.setOnClickListener {
//                                        addProduct()
//                                    }
//                                }
//
//                            }
//                            3 -> {
//                                getViewBinding().apply {
//                                addProductCard.visibility = View.VISIBLE
//                                lottieEmpty.visibility = View.GONE
//                                    tvLottieEmpty.visibility = View.GONE
//                                tvAddProduct.text = getString(R.string.add_3)
//                                    addProductCard.setOnClickListener {
//                                        addProduct()
//                                    }
//                            }
//
//                            }
//                            4 -> {
//                                getViewBinding().apply {
//                                    addProductCard.visibility = View.VISIBLE
//                                    lottieEmpty.visibility = View.GONE
//                                    tvLottieEmpty.visibility = View.GONE
//                                    tvAddProduct.text = getString(R.string.add_4)
//                                    addProductCard.setOnClickListener {
//                                        addProduct()
//                                    }
//                                }
//                            }
//                            5 -> {
//                                getViewBinding().apply {
//                                    addProductCard.visibility = View.VISIBLE
//                                    lottieEmpty.visibility = View.GONE
//                                    tvLottieEmpty.visibility = View.GONE
//                                    tvAddProduct.text = getString(R.string.add_5)
//                                    addProductCard.setOnClickListener {
//                                        AlertDialog.Builder(context)
//                                            .setTitle(getString(R.string.sorry))
//                                            .setMessage(getString(R.string.remove_first))
//                                            .setPositiveButton(getString(R.string.OK)) { positiveButton, _ ->
//                                                positiveButton.dismiss()
//                                            }
//                                            .show()
//                                    }
//                                }
//                            }
//                        }
//                        if(it.data?.size == 0){
//                            getViewBinding().apply {
//                                lottieEmpty.visibility = View.VISIBLE
//                                tvLottieEmpty.visibility = View.VISIBLE
//                                tvLottieEmpty.text = getString(R.string.no_products)
//                            }
//
//                        }else {
//                            getViewBinding().apply {
//                                lottieEmpty.visibility = View.GONE
//                                tvLottieEmpty.visibility = View.GONE
//                            }
//                        }
//                        getViewBinding().btnProduk.setBackgroundColor(Color.parseColor("#06283D"))
                    }
                    is Resource.Error -> {
                        var message = ""
                        when(it.message){
                            "HTTP 400 Bad Request" -> {
                                message = "Server Error"
                            }
                        }
                    }
                }
            }
        }
    }
    private fun productList(it: Resource<List<SellerProductResponseItem>>){
        showLoading(false)
        getViewBinding().apply {
            rvDiminati.visibility = View.GONE
            rvTerjual.visibility = View.GONE
            rvHistory.visibility = View.GONE
        }
        val availableProductSize = it.data?.filter { it.status == "available" }?.size
        if (it.data != null) {
            val saleProductAdapter =
                SaleProductAdapter(object  : SaleProductAdapter.OnclickListener{
                    var listCategory = ""
                    override fun onClickItem(data: SellerProductResponseItem) {
                        bundleEdit.apply {
                            putInt(PRODUCT_ID, data.id)
                            putString(USER_CITY, data.location)
                            for (kategori in data.categories){
                                listCategory += ", ${kategori.name}"
                            }
                            putString(PRODUCT_CATEGORY,listCategory.drop(2))
                            putString(PRODUCT_DESCRIPTION,data.description)
                            putString(PRODUCT_IMAGE,data.imageUrl)
                            putInt(PRODUCT_PRICE, data.basePrice)
                        }
                        if(data.status == "available") {
                            findNavController().navigate(
                                R.id.action_saleFragment_to_editProductFragment, bundleEdit
                            )
                        } else {
                            AlertDialog.Builder(context)
                                .setTitle(getString(R.string.warning))
                                .setMessage(getString(R.string.message_cant_edit_product))
                                .setPositiveButton(getString(R.string.OK)) { dialogP, _ ->
                                    dialogP.dismiss()
                                }
                                .setCancelable(false)
                                .show()
                        }
                    }
                })
            val sorted = it.data.sortedByDescending { it.id }
            saleProductAdapter.submitData(sorted)
            getViewBinding().rvProduct.adapter = saleProductAdapter
            getViewBinding().rvProduct.visibility = View.VISIBLE

        }
        when(availableProductSize){
            0 -> {
                getViewBinding().apply {
                    lottieEmpty.visibility = View.GONE
                    tvLottieEmpty.visibility = View.GONE
                    //buttonGrup.visibility = View.GONE
                    addProductCard.visibility = View.VISIBLE
                    addProductCard.setOnClickListener {
                        addProduct()
                    }
                    tvAddProduct.text = getString(R.string.add_0)
                }
            }
            1 -> {
                getViewBinding().apply {
                    lottieEmpty.visibility = View.GONE
                    tvLottieEmpty.visibility = View.GONE
                    addProductCard.visibility = View.VISIBLE
                    tvAddProduct.text = getString(R.string.add_1)
                    addProductCard.setOnClickListener {
                        addProduct()
                    }
                }

            }
            2 -> {
                getViewBinding().apply {
                    addProductCard.visibility = View.VISIBLE
                    lottieEmpty.visibility = View.GONE
                    tvLottieEmpty.visibility = View.GONE
                    tvAddProduct.text = getString(R.string.add_2)
                    addProductCard.setOnClickListener {
                        addProduct()
                    }
                }

            }
            3 -> {
                getViewBinding().apply {
                    addProductCard.visibility = View.VISIBLE
                    lottieEmpty.visibility = View.GONE
                    tvLottieEmpty.visibility = View.GONE
                    tvAddProduct.text = getString(R.string.add_3)
                    addProductCard.setOnClickListener {
                        addProduct()
                    }
                }

            }
            4 -> {
                getViewBinding().apply {
                    addProductCard.visibility = View.VISIBLE
                    lottieEmpty.visibility = View.GONE
                    tvLottieEmpty.visibility = View.GONE
                    tvAddProduct.text = getString(R.string.add_4)
                    addProductCard.setOnClickListener {
                        addProduct()
                    }
                }
            }
            5 -> {
                getViewBinding().apply {
                    addProductCard.visibility = View.VISIBLE
                    lottieEmpty.visibility = View.GONE
                    tvLottieEmpty.visibility = View.GONE
                    tvAddProduct.text = getString(R.string.add_5)
                    addProductCard.setOnClickListener {
                        AlertDialog.Builder(context)
                            .setTitle(getString(R.string.sorry))
                            .setMessage(getString(R.string.remove_first))
                            .setPositiveButton(getString(R.string.OK)) { positiveButton, _ ->
                                positiveButton.dismiss()
                            }
                            .show()
                    }
                }
            }
        }
        if(it.data?.size == 0){
            getViewBinding().apply {
                lottieEmpty.visibility = View.VISIBLE
                tvLottieEmpty.visibility = View.VISIBLE
                tvLottieEmpty.text = getString(R.string.no_products)
            }

        }else {
            getViewBinding().apply {
                lottieEmpty.visibility = View.GONE
                tvLottieEmpty.visibility = View.GONE
            }
        }
        getViewBinding().btnProduk.setBackgroundColor(Color.parseColor("#06283D"))

    }
    private fun addProduct() {
        findNavController().navigate(R.id.action_saleFragment_to_sellFragment)
    }

}