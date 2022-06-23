package com.preloved.app.ui.splash

import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.findNavController
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    FragmentSplashBinding::inflate
), SplashContract.View {
    private val viewModel: SplashViewModel by viewModel()

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
                    it?.access_token.let {
                        when (it) {
                            DatastoreManager.DEFAULT_ACCESS_TOKEN -> {
                                findNavController().navigate(R.id.action_splashFragment_to_authActivity)
                            }
                            else -> {
                                findNavController().navigate(R.id.action_splashFragment_to_mainActivity2)
                            }
                        }
                    }
                }, 3000)
            }
        }
    }

}