package com.preloved.app.ui.fragment.homepage.sale

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.databinding.FragmentSaleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SaleFragment : BaseFragment<FragmentSaleBinding, SaleViewModel>
    (FragmentSaleBinding::inflate), SaleContract.View  {
    override val viewModel: SaleViewModel by viewModel()
    private val bundle = Bundle()
    companion object {
        const val USER_TOKEN = "user_token"
    }
    override fun initView() {
        viewModel.userSession()
        getViewBinding().btnEdit.setOnClickListener {
            findNavController().navigate(R.id.action_saleFragment_to_editProfileFragment, bundle)
        }
    }

    override fun showLoading(isVisible: Boolean) {
        super.showLoading(isVisible)
        getViewBinding().pbLoadingUser.isVisible = isVisible
    }
    override fun observeData() {
        viewModel.userSessionResult().observe(viewLifecycleOwner) {
            if(it.access_token == DatastoreManager.DEFAULT_ACCESS_TOKEN){
                AlertDialog.Builder(context)
                    .setTitle("Warning")
                    .setMessage("Kamu Belum Login Nih")
                    .setPositiveButton("Login") { dialogP, _ ->
                        //ToLogin Fragment
                        findNavController().navigate(R.id.action_saleFragment_to_loginFragment3)
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
            viewModel.getUserDataResult().observe(viewLifecycleOwner){
                when (it) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        if(it.data != null) {
                            if(it.data.imageUrl != null ) {
                                Glide.with(requireContext())
                                    .load(it.data.imageUrl.toString())
                                    .placeholder(R.drawable.ic_profile)
                                    .transform(CenterCrop(), RoundedCorners(12))
                                    .into(getViewBinding().ivAvatarPenjual)
                            }
                            getViewBinding().apply {
                                tvNamaPenjual.setText(it.data.fullName)
                                tvKotaPenjual.setText(it.data.city)
                            }
                        }
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }

}