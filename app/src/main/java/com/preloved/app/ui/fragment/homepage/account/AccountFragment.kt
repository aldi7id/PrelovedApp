package com.preloved.app.ui.fragment.homepage.account

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.databinding.FragmentAccountBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : BaseFragment<FragmentAccountBinding, AccountViewModel>
    (FragmentAccountBinding::inflate), AccountContract.View {
    override val viewModel: AccountViewModel by viewModel()
    private val bundle = Bundle()
    companion object {
        const val USER_TOKEN = "user_token"
    }
    override fun showLoading(isVisible: Boolean) {
        super.showLoading(isVisible)
        getViewBinding().pbLoading.isVisible = isVisible
    }
    override fun initView() {

        viewModel.userSession()
        getViewBinding().apply {
            tvChangeProfile.setOnClickListener{
                findNavController().navigate(R.id.action_accountFragment_to_editProfileFragment,bundle)
            }
            tvExit.setOnClickListener {
                AlertDialog
                    .Builder(requireContext())
                    .setTitle("Konfirmasi Keluar")
                    .setMessage("Yakin ingin keluar?")
                    .setPositiveButton("Iya") { dialogPositive, _ ->
                        viewModel.deleteToken()
                        Toast
                            .makeText(
                                requireContext(),
                                "Logout Success",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                        findNavController().navigate(R.id.homeFragment)
                        dialogPositive.dismiss()
                    }
                    .setNegativeButton("Tidak") { dialogNegative, _ ->
                        dialogNegative.dismiss()
                    }
                    .setCancelable(false)
                    .show()
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
                bundle.putString(USER_TOKEN,it.access_token)
                viewModel.getUserData(it.access_token)
            }
        }
        viewModel.getUserDataResult().observe(viewLifecycleOwner){
                    when (it) {
                        is Resource.Loading -> {
                            showLoading(true)
                        }
                        is Resource.Success -> {
                            showLoading(false)
                            if(it.data != null) {
                                Glide.with(requireContext())
                                    .load(it.data.imageUrl.toString())
                                    .placeholder(R.drawable.ic_profile)
                                    .transform(CenterCrop(), RoundedCorners(12))
                                    .into(getViewBinding().ivProfile)
                            }
                        }
                        is Resource.Error -> {

                        }
                    }
                }
    }


}