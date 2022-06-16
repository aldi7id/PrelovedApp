package com.preloved.app.ui.login

import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.databinding.FragmentLoginBinding

class LoginFragment(override val viewModel: LoginViewModel) : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    FragmentLoginBinding::inflate
), LoginContract.View {
    override fun initView() {
    }

    override fun postData() {

    }


}