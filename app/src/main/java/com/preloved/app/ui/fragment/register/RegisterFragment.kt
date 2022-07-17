package com.preloved.app.ui.fragment.register

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.databinding.FragmentRegisterBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(
    FragmentRegisterBinding::inflate
), RegisterContract.View {
    override val viewModel: RegisterViewModel by viewModel()
    override fun initView() {
        observeData()
        onClick()
    }

    private fun onClick() {
        getViewBinding().apply {
            btnRegister.setOnClickListener {
                authRegisterUser()
            }
            tvGoToLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment2_to_loginFragment3)
            }
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun authRegisterUser() {
        getViewBinding().apply {
            if (checkFormValidation()){
                viewModel.registerUser(
                    RegisterRequest(
                        id = null,
                        fullName =  etNama.text.toString(),
                        email = etEmail.text.toString(),
                        password = etPassword.text.toString(),
                        phoneNumber = etPhone.text.toString().toLong(),
                        city = etCity.text.toString(),
                        address = etAddress.text.toString(),
                        imageUrl = null
                    )
                )
            }
        }
    }

    override fun checkFormValidation(): Boolean {
        getViewBinding().apply {
            var isValid = true

            when {
                etNama.text.toString().isEmpty() -> {
                    tfNama.error = "Please fill your name"
                    tfNama.isErrorEnabled = true
                    isValid = false
                }
                etNama.text!!.length < 3 -> {
                    tfNama.isErrorEnabled = true
                    tfNama.error = "Minimum Name 3 Character"
                    isValid = false
                }
                etEmail.text.toString().isEmpty() -> {
                    tfEmail.error = "Please fill your valid email"
                    tfEmail.isErrorEnabled = true
                    isValid = false
                }
                etPassword.text.toString().isEmpty() -> {
                    tfPassword.error = "Please fill your password"
                    tfPassword.isErrorEnabled = true
                    isValid = false
                }
                etRepassword.text.toString().isEmpty() -> {
                    tfRepassword.error = "Please fill your repassword"
                    tfRepassword.isErrorEnabled = true
                    isValid = false
                }
                etPhone.text.toString().isEmpty() -> {
                    tfPhone.error = "Please fill your phone number"
                    tfPhone.isErrorEnabled = true
                    isValid = false
                }
                etCity.text.toString().isEmpty() -> {
                    tfCity.error = "Please fill your city"
                    tfCity.isErrorEnabled = true
                    isValid = false
                }
                etAddress.text.toString().isEmpty() -> {
                    tfAddress.error = "Please fill your address"
                    tfAddress.isErrorEnabled = true
                    isValid = false
                }
                etPassword.text.toString() != etRepassword.text.toString() -> {
                    tfPassword.error = "Password must same"
                    tfRepassword.error = "Password must same"
                    tfPassword.isErrorEnabled = true
                    tfRepassword.isErrorEnabled = true
                    isValid = false
                }

                etPassword.text!!.length < 8 -> {
                    tfPassword.isErrorEnabled = true
                    tfPassword.error = "Minimum Password 8 Character"
                    isValid = false
                }
                else -> {
                    tfPassword.error = null
                    tfRepassword.error = null
                    tfPhone.error = null
                    tfNama.error = null
                    tfEmail.error = null
                    tfAddress.error = null
                }
            }
            return isValid
        }
    }


    override fun observeData() {
        showLoading(false)
        viewModel.apply {
            getRegisterUserLiveData().observe(viewLifecycleOwner) {
                when(it){
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        Toast.makeText(requireContext(), "Register Success", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        Toast.makeText(requireContext(), "Register Failed, Account Already Exist", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


}