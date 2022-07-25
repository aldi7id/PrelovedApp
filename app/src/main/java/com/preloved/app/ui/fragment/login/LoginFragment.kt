package com.preloved.app.ui.fragment.login

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    FragmentLoginBinding::inflate
), LoginContract.View {
    override val viewModel: LoginViewModel by viewModel()

    override fun initView() {
        onClick()
        onPressBack()
    }

    private fun onClick() {
        getViewBinding().apply {
            btnLogin.setOnClickListener {
                loginAuthUser()
            }
            tvNoHaveAccount.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment3_to_registerFragment2)
            }
        }
    }

    override fun observeData() {
        viewModel.postLoginUserResult().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showError(false)
                }
                is Resource.Success -> {
                    showError(false)
                    Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                is Resource.Error -> {
                    showError(true, it.message)
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
    private fun onPressBack() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        })
    }
}