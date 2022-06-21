package com.preloved.app.ui.profile.edit

import android.app.Activity
import android.content.ContextWrapper
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.response.UserResponse
import com.preloved.app.databinding.FragmentEditProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class EditProfileFragment() : BaseFragment<FragmentEditProfileBinding, EditProfileViewModel>(
    FragmentEditProfileBinding::inflate
), EditProfileContract.View {
    private var selectedPicture: File? = null
    override val viewModel: EditProfileViewModel by viewModel()

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
            val name = etNama.text.toString()
            val phone = etPhone.text.toString()
            val address = etAddress.text.toString()
            val city = etCity.text.toString()

            when {
                name.isEmpty() -> {
                    isFormValid = false
                    tfNama.isErrorEnabled = true
                    tfNama.error = "Nama Tidak Boleh Kosong"
                }
                phone.isEmpty() || phone.length > 14 -> {
                    isFormValid = false
                    tfPhone.isErrorEnabled = true
                    tfPhone.error = "Nomor HP Tidak Boleh Kosong / Lebih Dari 14 Angka"
                }
                city.isEmpty() -> {
                    isFormValid = false
                    tfCity.isErrorEnabled = true
                    tfCity.error = "City Tidak Boleh Kosong"
                }
                address.isEmpty() -> {
                    tfAddress.isErrorEnabled = true
                    tfAddress.error = "Address Tidak Boleh Kosong"

                    isFormValid = false
                }
                else -> {
                    tfAddress.isErrorEnabled = false
                    tfNama.isErrorEnabled = false
                    tfCity.isErrorEnabled = false
                    tfPhone.isErrorEnabled = false
                }
            }
            return isFormValid
        }

    }



    override fun getData() {
        viewModel.getProfileData()
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when(resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    Glide.with(requireContext()).load(fileUri).circleCrop().into(getViewBinding().ivProfile)
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
    override fun observeData() {
        super.observeData()
        viewModel.getProfileLiveData().observe(viewLifecycleOwner) { response ->
            when(response){
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
        viewModel.getChangeProfileResultLiveData().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    Toast.makeText(requireContext(), "Change Profile Success!", Toast.LENGTH_SHORT).show()
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

    override fun showLoading(isVisible: Boolean) {
        super.showLoading(isVisible)
        getViewBinding().pbLoading.isVisible = isVisible
    }

    override fun showContent(isVisible: Boolean) {
        super.showContent(isVisible)
        getViewBinding().groupContent.isVisible = isVisible
    }
    override fun setDataToView(data: UserResponse) {
        getViewBinding().apply {
            etEmail.setText(data.email)
            etNama.setText(data.fullName)
            etCity.setText(data.city)
            etAddress.setText(data.address)
            etPhone.setText(data.phoneNumber)
            Glide.with(requireContext()).load(data.imageUrl).circleCrop().into(getViewBinding().ivProfile)
//            tfAddress.error = null
//            tfNama.error = null
//            tfPhone.error = null
//            tfCity.error = null
        }
    }
    override fun setOnClickListeners() {
        getViewBinding().apply {
            btnChange.setOnClickListener {
            if (checkFormValidation()){
                viewModel.updateProfileData(
                    email = etEmail.text.toString(),
                    nama = etNama.text.toString(),
                    city = etCity.text.toString(),
                    address = etAddress.text.toString(),
                    phone = etPhone.text.toString(),
                    profilePhoto = selectedPicture,
                )
            }
        }
            flProfilePict.setOnClickListener {
                ImagePicker.with(this@EditProfileFragment)
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
}