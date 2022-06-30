package com.preloved.app.ui.product.add


import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.data.network.model.response.PostProductResponse
import com.preloved.app.databinding.FragmentAddProductBinding
import com.preloved.app.di.dataSourceModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class AddProductFragment : BaseFragment<FragmentAddProductBinding, AddProductViewModel>(
    FragmentAddProductBinding::inflate
) , AddProductContract.View {
    private var selectedPicture: File? = null
    override val viewModel: AddProductViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
    }

    override fun initView() {
        setOnClickListeners()
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
            ArrayAdapter(
                it, R.layout.category_item,
                type
            )
        }
        val editTextFilledExposedDropdown: AutoCompleteTextView =
            getViewBinding().filledCategory
        editTextFilledExposedDropdown.setAdapter(adapter)

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
                        name = productName,
                        base_price = productPrice,
                        category = 18,
                        description = productDesc,
                        location = "Jakarta",
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
                ImagePicker.with(this@AddProductFragment)
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
    }

}