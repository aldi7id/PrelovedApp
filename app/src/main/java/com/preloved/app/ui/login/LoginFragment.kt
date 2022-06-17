package com.preloved.app.ui.login

import android.widget.Toast
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    FragmentLoginBinding::inflate
), LoginContract.View {
    private val viewModel: LoginViewModel by viewModel()

    override fun initView() {
        onClick()
    }

    private fun onClick() {
        getViewBinding().apply {
            btnLogin.setOnClickListener {
                loginAuthUser()
            }
        }
    }

    override fun observeData() {
        viewModel.postLoginUserResult().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "${it.data?.accessToken}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun loginAuthUser() {
        with(getViewBinding()) {
            viewModel.postLoginDataUser(
                LoginRequest(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            )
        }
    }
}