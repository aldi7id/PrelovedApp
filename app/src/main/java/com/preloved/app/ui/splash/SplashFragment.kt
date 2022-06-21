package com.preloved.app.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    FragmentSplashBinding::inflate
), SplashContract.View {

    override fun initView() {
        HandlerSplash()
    }

    override fun HandlerSplash() {
        getViewBinding()
        getViewModel().apply {
            SplashSession()
        }
    }

    override fun observeData() {
        getViewModel().apply {
            SplashSessionResult().observe(viewLifecycleOwner) {
                Handler(Looper.getMainLooper()).postDelayed({
                    if (it.access_token != null) {
//                    findNavController().navigate(R.id.)
                    } else {
//                    findNavController().navigate(R.id)
                    }
                }, 3000)
            }
        }
    }

}