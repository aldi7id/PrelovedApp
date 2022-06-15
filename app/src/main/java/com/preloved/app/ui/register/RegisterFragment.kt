package com.preloved.app.ui.register

import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.data.network.model.request.auth.RegisterRequest
import com.preloved.app.databinding.FragmentRegisterBinding


class RegisterFragment : BaseFragment<FragmentRegisterBinding, RegisterViewModel>(
    FragmentRegisterBinding::inflate
), RegisterContract.View {

    override fun initView() {
        observeData()
    }

    override fun checkFormValidation(): Boolean {
        TODO("Not yet implemented")
    }


    override fun observeData() {

    }
    private fun registerAction(
        full_name : String,
        email : String,
        password: String,
        phone_number: Long,
        address : String,
        image_url : Any
    ){
        val request = RegisterRequest(
            fullName = full_name,
            email = email,
            password = password,
            address = address,
            phoneNumber = phone_number,
            imageUrl = image_url,
            id = null
        )
    }


}