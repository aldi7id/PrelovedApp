package com.preloved.app.ui.fragment.homepage.notification

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.data.network.model.response.NotificationResponse
import com.preloved.app.databinding.FragmentNotificationBinding
import com.preloved.app.databinding.FragmentSellBinding
import com.preloved.app.ui.fragment.homepage.sell.SellContract
import com.preloved.app.ui.fragment.homepage.sell.SellFragment
import com.preloved.app.ui.fragment.homepage.sell.SellViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : BaseFragment<FragmentNotificationBinding, NotificationViewModel>(
    FragmentNotificationBinding::inflate
) , NotificationContract.View {
    override val viewModel: NotificationViewModel by viewModel()
    private var token: String = ""
    override fun initView() {
        setOnClickListeners()
        viewModel.userSession()
        viewModel.userSessionResult().observe(viewLifecycleOwner){
            if(it.access_token == DatastoreManager.DEFAULT_ACCESS_TOKEN) {
                AlertDialog.Builder(context)
                    .setTitle(getString(R.string.warning))
                    .setMessage(getString(R.string.please_login))
                    .setPositiveButton(getString(R.string.login)) { dialogP, _ ->
                        //ToLogin Fragment
                        findNavController().navigate(R.id.action_notificationFragment_to_loginFragment3)
                        dialogP.dismiss()
                    }
                    .setNegativeButton(getString(R.string.later)) { dialogN, _ ->
                        //ToHomeFragment
                        findNavController().navigate(R.id.homeFragment)
                        dialogN.dismiss()
                    }
                    .setCancelable(false)
                    .show()
            } else {
                token = it.access_token
                //viewModel.getUserData(it.access_token)
                Log.d ("token", token)
                viewModel.getNotification(token)
                //bundle.putString(SellFragment.USER_TOKEN,it.access_token)
            }
        }

        viewModel.getNotificationResult().observe(viewLifecycleOwner){
            when (it) {
                is Resource.Loading -> {
                    showError(false)
                }
                is Resource.Success -> {
                    if (it.data!!.isNotEmpty()){
                        val notificationAdapter =
                            NotificationAdapter(object : NotificationAdapter.OnClickListener {
                                override fun onClickItem(data: NotificationResponse) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Notif Id = ${data.id}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                        notificationAdapter.submitData(it.data)
                        getViewBinding().rvNotification.adapter = notificationAdapter
                    } else {
                        getViewBinding().emptyNotif.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    showError(true, it.message)
                }
            }
        }
    }

    override fun setOnClickListeners() {

    }


}
