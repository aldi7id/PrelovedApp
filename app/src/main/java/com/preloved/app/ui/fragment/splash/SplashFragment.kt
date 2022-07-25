package com.preloved.app.ui.fragment.splash

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    FragmentSplashBinding::inflate
), SplashContract.View {
    override val viewModel: SplashViewModel by viewModel()

    override fun initView() {
        handlerSplash()
    }

    override fun handlerSplash() {
        viewModel.apply {
            splashSession()
        }
    }

    override fun observeData() {
        viewModel.apply {
            splashSessionResult().observe(viewLifecycleOwner) {
                Handler(Looper.getMainLooper()).postDelayed({
                    findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                }, 3000)
            }
        }
    }

}