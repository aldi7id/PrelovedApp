package com.preloved.app.ui.fragment.homepage.sell

import android.app.ActionBar
import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.databinding.FragmentSellBinding
import com.preloved.app.ui.listCategory
import com.preloved.app.ui.listCategoryId
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class SellFragment : BaseFragment<FragmentSellBinding, SellViewModel>(
    FragmentSellBinding::inflate
) , SellContract.View {
    private var citySeller: String = ""
    private var uri: String = ""
    private var token: String = ""

    override fun showLoading(isVisible: Boolean) {
        super.showLoading(isVisible)
        getViewBinding().pbLoading.isVisible = isVisible
    }
    private var selectedPicture: File? = null
    override val viewModel: SellViewModel by viewModel()

    override fun initView() {
        viewModel.userSession()
        getData()
        setOnClickListeners()
        viewModel.categoryList.observe(viewLifecycleOwner) { kat ->
            if (kat.isNotEmpty()) {
                var kategori = ""
                for (element in kat) {
                    kategori += ", $element"
                }
                getViewBinding().etKategory.setText(kategori.drop(2))
                Log.d("HAYO 1", listCategoryId.toString())
            } else
                getViewBinding().etKategory.setText("Pilih Kategory")
            Log.d("HAYO 1", listCategoryId.toString())
        }
    }

    override fun checkFormValidation(): Boolean {
        getViewBinding().apply {
            var isFormValid = true
            var nameProduct = etNamaProduk.text.toString()
            when {
                nameProduct.isEmpty() -> {
                    isFormValid = false
                    tfNamaProduk.isErrorEnabled = true
                    tfNamaProduk.error = "Harap Masukkan Nama Produk"
                    tfNamaProduk.helperText
                } else -> {
                tfNamaProduk.isErrorEnabled = false
            }
            }
            return isFormValid
        }

    }

    override fun setDataToView(data: List<CategoryResponseItem>) {
        getViewBinding().apply {
            //etNamaProduk.setText(data.name)
        }
        val type = data.toTypedArray().map { it.name }

        val adapter = activity?.let {
            val adapter = ArrayAdapter(
                it, R.layout.category_item,
                type
            )
            //getViewBinding().filledCategory.setAdapter(adapter)
//            val categoryAdapter = CategoryAdapter{
//                getViewBinding().filledCategory.onItemClickListener = object : AdapterView.OnItemClickListener{
//                    override fun onItemClick(
//                        parent: AdapterView<*>?,
//                        view: View?,
//                        position: Int,
//                        id: Long
//                    ) {
//                        type[position]
//                        Log.d("ID", type[position].toString())
//                    }
//
//                }
//
//
//            }
           // getViewBinding().filledCategory.setAdapter(categoryAdapter)

        }


    }

    override fun getData() {
        viewModel.getCategoryData()
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when(resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    Glide.with(requireContext())
                        .apply {
                            RequestOptions().override(300,320)
                        }
                        .load(fileUri)
                        .into(getViewBinding().ibAddImage)
                    fileUri.path?.let {
                        val file = File(it)
                        if (file.exists()) {
                            selectedPicture = file
                        }
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Tast Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }
    override fun setOnClickListeners() {
        getViewBinding().apply {

            btnJual.setOnClickListener {
                if (checkFormValidation()){
                    val productName = etNamaProduk.text.toString()
                    val productPrice = etHargaProduk.text.toString().toInt()
                    val productDesc = etDeskripsi.text.toString()
                    viewModel.postProductData(
                        token = token,
                        name = productName,
                        base_price = productPrice,
                        category = listCategoryId,
                        description = productDesc,
                        location = citySeller,
                        image = selectedPicture
                    )
                }
            }
            viewModel.getChangeProfileResultLiveData().observe(viewLifecycleOwner){ response ->
                when(response) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        showToastSuccess()
                        findNavController().navigate(R.id.action_sellFragment_to_saleFragment)
                        Toast.makeText(requireContext(), "Add Product Success!", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        showError(true, response.message)
                        var message = ""
                        when(response.message){
                            "HTTP 400 Bad Request" -> {
                                message = "Max Upload 5 Product"
                            }
                        }
                        AlertDialog.Builder(context)
                            .setTitle("Maaf")
                            .setMessage(message)
                            .setPositiveButton("Mengerti") { positiveButton, _ ->
                                positiveButton.dismiss()
                            }
                            .show()
                    }
                }

            }
            ibAddImage.setOnClickListener{
                ImagePicker.with(this@SellFragment)
                    .crop()
                    .saveDir(
                        File(
                            getActivity()?.getExternalCacheDir(),
                            "ImagePicker"
                        )
                    )
                    .compress(1024)
                    .maxResultSize(1080,1080)
                    .createIntent {
                        startForProfileImageResult.launch(it)
                    }
            }
            etKategory.setOnClickListener {
                val bottomFragment = BottomSheetChooseCategoryFragment(
                    update = {
                        viewModel.addCategory(listCategory)
                    })
                bottomFragment.show(parentFragmentManager, "Tag")
            }
        }

    }

    override fun observeData() {
        super.observeData()
        viewModel.getCategoryLiveData().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    response.data?.let { setDataToView(it) }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(true)
                    showError(true, response.message)
                }
            }
        }
        getViewBinding().apply {
            viewModel.userSessionResult().observe(viewLifecycleOwner){
                if(it.access_token == DatastoreManager.DEFAULT_ACCESS_TOKEN) {
                    AlertDialog.Builder(context)
                        .setTitle("Warning")
                        .setMessage("Kamu Belum Login Nih")
                        .setPositiveButton("Login") { dialogP, _ ->
                            //ToLogin Fragment
                            findNavController().navigate(R.id.action_sellFragment_to_loginFragment3)
                            dialogP.dismiss()
                        }
                        .setNegativeButton("Tidak") { dialogN, _ ->
                            //ToHomeFragment
                            findNavController().navigate(R.id.homeFragment)
                            dialogN.dismiss()
                        }
                        .setCancelable(false)
                        .show()
                } else {
                    token = it.access_token
                    viewModel.getUserData(it.access_token)
                    Log.d ("token", token)
                }
            }
            viewModel.getUserDataResult().observe(viewLifecycleOwner){
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        if(it.data != null){
                            val city = it.data.city
                            val address = it.data.address
                            val photo = it.data.imageUrl ?: "noImage"
                            val phone = it.data.phoneNumber
                            if(city.isEmpty() || address.isEmpty() || photo == "noImage" || phone.isEmpty()){
                                AlertDialog.Builder(requireContext())
                                    .setTitle("Warning")
                                    .setMessage("Lengkapi data terlebih dahulu yuk sebelum Jual Barang")
                                    .setPositiveButton("Iya"){ positiveButton, _ ->
                                        findNavController().navigate(R.id.action_sellFragment_to_editProfileFragment)
                                        positiveButton.dismiss()
                                    }
                                    .setNegativeButton("Tidak") { negativeButton, _ ->
                                        findNavController().popBackStack()
                                        negativeButton.dismiss()
                                    }
                                    .show()
                            } else {
                                citySeller = it.data.city
                                Log.d("City", citySeller)
                                //Bundle

                            }
                        }
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        AlertDialog.Builder(requireContext())
                            .setMessage(it.message)
                            .setPositiveButton("Ok") { dialog, _ ->
                                dialog.dismiss()
                                findNavController().popBackStack()
                            }
                            .show()
                    }
                }
            }

        }
    }
    private fun showToastSuccess() {
        val snackBarView =
            Snackbar.make(getViewBinding().root, "Produk berhasil di terbitkan.", Snackbar.LENGTH_LONG)
        val layoutParams = ActionBar.LayoutParams(snackBarView.view.layoutParams)
        snackBarView.setAction(" ") {
            snackBarView.dismiss()
        }
        val textView =
            snackBarView.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_add, 0)
        textView.compoundDrawablePadding = 16
        layoutParams.gravity = Gravity.TOP
        layoutParams.setMargins(32, 150, 32, 0)
        snackBarView.view.setPadding(24, 16, 0, 16)
        snackBarView.view.setBackgroundColor(resources.getColor(R.color.primary))
        snackBarView.view.layoutParams = layoutParams
        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()
    }
}
