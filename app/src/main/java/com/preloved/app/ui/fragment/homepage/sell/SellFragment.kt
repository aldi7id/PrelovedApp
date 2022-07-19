package com.preloved.app.ui.fragment.homepage.sell

import android.app.ActionBar
import android.app.Activity
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
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
import com.preloved.app.ui.uriToFile
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class SellFragment : BaseFragment<FragmentSellBinding, SellViewModel>(
    FragmentSellBinding::inflate
) , SellContract.View {
    private var citySeller: String = ""
    private var uri: String = ""
    private var token: String = ""
    private val bundle = Bundle()
    companion object {
        const val USER_TOKEN = "user_token"
        const val TITLE_PRODUCT = "title_product"
        const val CITY_USER = "city"
        const val PRICE_PRODUCT = "price"
        const val DESC_PRODUCT = "desc"
        const val NAME_USER = "name"
        const val IMAGE_USER = "image_user"
        const val CATEGORY_PRODUCT = "category"
        const val IMAGE_PRODUCT ="image_product"
    }



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

            } else
                getViewBinding().etKategory.setText(getString(R.string.choose_category))

        }
        getViewBinding().apply {
            etNamaProduk.addTextChangedListener(textWatcher)
            etHargaProduk.addTextChangedListener(textWatcher)
            etDeskripsi.addTextChangedListener(textWatcher)
            etKategory.addTextChangedListener(textWatcher)
        }
    }
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            getViewBinding().apply {
                tfDeksipsi.error = null
                tfHargaProduk.error = null
                tfKategory.error = null
                tfNamaProduk.error = null
            }
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }



        override fun checkFormValidation(): Boolean {
        getViewBinding().apply {
            var isFormValid = true
            val nameProduct = etNamaProduk.text.toString()
            val priceProduct = etHargaProduk.text.toString()
            val descProduct = etDeskripsi.text.toString()
            val categoryProduct = etKategory.text.toString()
            when {
                nameProduct.isEmpty() -> {
                    isFormValid = false
                    tfNamaProduk.isErrorEnabled = true
                    tfNamaProduk.error = getString(R.string.enter_name_product)
                }
                priceProduct.isEmpty() -> {
                    isFormValid = false
                    tfHargaProduk.isErrorEnabled = true
                    tfHargaProduk.error = getString(R.string.enter_price_product)
                }
                categoryProduct.isEmpty() -> {
                    isFormValid = false
                    tfKategory.isErrorEnabled = true
                    tfKategory.error = getString(R.string.please_choose_category)

                }
                descProduct.isEmpty() -> {
                    isFormValid = false
                    tfDeksipsi.error = getString(R.string.enter_desc_product)
                }
                uri.isEmpty() -> {
                    isFormValid = false
                    Toast.makeText(context, getString(R.string.inser_image_first), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    tfNamaProduk.isErrorEnabled = false
                    tfHargaProduk.isErrorEnabled = false
                    tfDeksipsi.isErrorEnabled = false
                    tfKategory.isErrorEnabled = false
                }
            }
            return isFormValid
        }
    }

    override fun setDataToView(data: List<CategoryResponseItem>) {
//        getViewBinding().apply {
//            //etNamaProduk.setText(data.name)
//        }
//        val type = data.toTypedArray().map { it.name }

//        val adapter = activity?.let {
//            val adapter = ArrayAdapter(
//                it, R.layout.category_item,
//                type
//            )
//            //getViewBinding().filledCategory.setAdapter(adapter)
////            val categoryAdapter = CategoryAdapter{
////                getViewBinding().filledCategory.onItemClickListener = object : AdapterView.OnItemClickListener{
////                    override fun onItemClick(
////                        parent: AdapterView<*>?,
////                        view: View?,
////                        position: Int,
////                        id: Long
////                    ) {
////                        type[position]
////                        Log.d("ID", type[position].toString())
////                    }
////
////                }
////
////
////            }
//           // getViewBinding().filledCategory.setAdapter(categoryAdapter)
//
//        }


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
                    uri = fileUri.toString()
                    Glide.with(requireContext())
                        .load(uri)
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
                    Toast.makeText(requireContext(), getString(R.string.task_cancelled), Toast.LENGTH_SHORT).show()
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
                        image = uriToFile(Uri.parse(uri), requireContext())
                    )
                }
            }
            btnPreview.setOnClickListener {
                if (checkFormValidation()){
                    val productName = etNamaProduk.text.toString()
                    val productPrice = etHargaProduk.text.toString().toInt()
                    val productDesc = etDeskripsi.text.toString()
                    val categoryProduct = etKategory.text.toString()

                    bundle.putString(TITLE_PRODUCT, productName)
                    bundle.putString(PRICE_PRODUCT, productPrice.toString())
                    bundle.putString(DESC_PRODUCT, productDesc)
                    bundle.putString(CATEGORY_PRODUCT, categoryProduct)
                    bundle.putString(IMAGE_PRODUCT, uri)

                    findNavController().navigate(R.id.action_sellFragment_to_previewProductFragment, bundle)
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
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        showError(true, response.message)
                        var message = ""
                        when(response.message){
                            "HTTP 400 Bad Request" -> {
                                message = getString(R.string.max_upload)
                            }
                        }
                        AlertDialog.Builder(context)
                            .setTitle(getString(R.string.sorry))
                            .setMessage(message)
                            .setPositiveButton(getString(R.string.OK)) { positiveButton, _ ->
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
                            activity?.externalCacheDir,
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
                        .setTitle(getString(R.string.warning))
                        .setMessage(getString(R.string.please_login))
                        .setPositiveButton(getString(R.string.login)) { dialogP, _ ->
                            //ToLogin Fragment
                            findNavController().navigate(R.id.action_sellFragment_to_loginFragment3)
                            dialogP.dismiss()
                        }
                        .setNegativeButton(getString(R.string.later)) { dialogN, _ ->
                            //ToHomeFragment
                            findNavController().navigate(R.id.homeFragment)
                            dialogN.dismiss()
                        }
                        .setCancelable(false)
                        .show()
                } else {
                    token = it.access_token
                    viewModel.getUserData(it.access_token)
                    bundle.putString(USER_TOKEN,it.access_token)
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
                            val photo = it.data.imageUrl
                            val phone = it.data.phoneNumber
                            if(city.isEmpty() || address.isEmpty() || photo == "noImage" || phone.isEmpty()){
                                AlertDialog.Builder(requireContext())
                                    .setTitle(getString(R.string.warning))
                                    .setMessage(getString(R.string.message_complate_account))
                                    .setPositiveButton(getString(R.string.OK)){ positiveButton, _ ->
                                        findNavController().navigate(R.id.action_sellFragment_to_editProfileFragment)
                                        positiveButton.dismiss()
                                    }
                                    .setNegativeButton(getString(R.string.later)) { negativeButton, _ ->
                                        findNavController().popBackStack()
                                        negativeButton.dismiss()
                                    }
                                    .show()
                            } else {
                                citySeller = it.data.city
                                bundle.putString(CITY_USER,it.data.city)
                                bundle.putString(NAME_USER, it.data.fullName)
                                bundle.putString(IMAGE_USER, it.data.imageUrl.toString())

                            }
                        }
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        AlertDialog.Builder(requireContext())
                            .setMessage(it.message)
                            .setPositiveButton(getString(R.string.OK)) { dialog, _ ->
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
            Snackbar.make(getViewBinding().root, getString(R.string.toast_product_add), Snackbar.LENGTH_LONG)
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
}
