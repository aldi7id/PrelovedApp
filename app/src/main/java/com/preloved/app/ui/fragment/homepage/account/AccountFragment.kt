package com.preloved.app.ui.fragment.homepage.account

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.common.Constant
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.data.local.datastore.DatastorePreferences
import com.preloved.app.databinding.FragmentAccountBinding
import com.preloved.app.databinding.FragmentRegisterBinding
import com.preloved.app.ui.fragment.register.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : BaseFragment<FragmentAccountBinding, AccountViewModel>
    (FragmentAccountBinding::inflate), AccountContract.View {
    override val viewModel: AccountViewModel by viewModel()
    override fun initView() {
        viewModel.userSession()
        getViewBinding().apply {
            tvChangeProfile.setOnClickListener{
                findNavController().navigate(R.id.action_accountFragment_to_editProfileFragment)
            }
        }
    }

    override fun observeData() {
        viewModel.userSessionResult().observe(viewLifecycleOwner) {
            if(it.access_token == DatastoreManager.DEFAULT_ACCESS_TOKEN){
                AlertDialog.Builder(context)
                    .setTitle("Warning")
                    .setMessage("Kamu Belum Login Nih")
                    .setPositiveButton("Login") { dialogP, _ ->
                        //ToLogin Fragment
                        findNavController().navigate(R.id.action_accountFragment_to_loginFragment3)
                        dialogP.dismiss()
                    }
                    .setNegativeButton("Tidak") { dialogN, _ ->
                        //ToHomeFragment
                        findNavController().navigate(R.id.homeFragment)
                        dialogN.dismiss()
                    }
                    .setCancelable(false)
                    .show()
                //viewModel.checkLogin().removeObserver(viewLifecycleOwner)
            } else {
                //GetUserToken
                //VM Get User
            }
        }
    }


}