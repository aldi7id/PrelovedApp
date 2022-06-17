package com.preloved.app.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.databinding.FragmentRegisterBinding


//class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(
//    FragmentRegisterBinding::inflate
//), RegisterContract.View {
//
//    override fun initView() {
//        onClick()
//        observeData()
//    }
//
//    private fun onClick() {
//        getViewBinding().apply {
//            tvSignIn.setOnClickListener {
//                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
//            }
//            btnRegister.setOnClickListener {
//                authRegisterUser()
//            }
//        }
//    }
//
//    override fun checkFormValidation(): Boolean {
//        getViewBinding().apply {
//            var isValid = true
//
//            when {
//                etRegisterName.text.toString().isEmpty() -> {
//                    tfRegisterName.error = "Fill the name"
//                    isValid = false
//                }
//                etRegisterEmail.text.toString().isEmpty() -> {
//                    tfRegisterEmail.error = "Fill the email"
//                    isValid = false
//                }
//                etRegisterAge.text.toString().isEmpty() -> {
//                    tfRegisterAge.error = "Fill the age"
//                    isValid = false
//                }
//                etRegisterPhoneNumber.text.toString().isEmpty() -> {
//                    tfRegisterPhoneNumber.error = "Fill the phone"
//                    isValid = false
//                }
//                etRegisterUsername.text.toString().isEmpty() -> {
//                    tfRegisterUsername.error = "Fill the username"
//                    isValid = false
//                }
//                etRegisterPassword.text.toString().isEmpty() -> {
//                    tfRegisterPassword.error = "Fill the password"
//                    isValid = false
//                }
//                else -> {
//                    tfRegisterName.error = null
//                    tfRegisterEmail.error = null
//                    tfRegisterAge.error = null
//                    tfRegisterPhoneNumber.error = null
//                    tfRegisterUsername.error = null
//                    tfRegisterPassword.error = null
//                }
//            }
//            return isValid
//        }
//    }
//
//    override fun observeData() {
//        showLoading(false)
//        getViewModel().apply {
//            getRegisterUserLiveData().observe(viewLifecycleOwner) {
//                when (it) {
//                    is Resource.Loading -> {
//                        showLoading(true)
//                    }
//                    is Resource.Success -> {
//                        showLoading(false)
//                        Toast.makeText(requireContext(), "Register Success", Toast.LENGTH_SHORT)
//                            .show()
//                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
//                        getViewModel().getRegisterUserLiveData().removeObservers(viewLifecycleOwner)
//                    }
//                    is Resource.Error -> {
//                        showLoading(false)
//                        Toast.makeText(
//                            requireContext(),
//                            "Register Fail, Please make sure the fill",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            }
//
//        }
//    }
//
//    private fun authRegisterUser() {
//        getViewBinding().apply {
//            if (checkFormValidation()) {
//                getViewModel().registerUser(
//                    UserEntity(
//                        id = null,
//                        name = etRegisterName.text.toString(),
//                        profile = null,
//                        email = etRegisterEmail.text.toString(),
//                        age = etRegisterAge.text.toString().toInt(),
//                        phone_number = etRegisterPhoneNumber.text.toString(),
//                        username = etRegisterUsername.text.toString(),
//                        password = etRegisterPassword.text.toString()
//                    )
//                )
//            }
//
//        }
//    }
//
//}