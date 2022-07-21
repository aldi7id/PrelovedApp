package com.preloved.app.ui.fragment.homepage.notification

import android.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.data.network.model.response.NotificationResponse
import com.preloved.app.databinding.FragmentNotificationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : BaseFragment<FragmentNotificationBinding, NotificationViewModel>(
    FragmentNotificationBinding::inflate
) , NotificationContract.View {
    override val viewModel: NotificationViewModel by viewModel()
    private var token: String = ""
    override fun showLoading(isVisible: Boolean) {
        super.showLoading(isVisible)
        getViewBinding().pbLoading.isVisible = isVisible
    }
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
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
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
                        val sorted = it.data.sortedByDescending { it.id }
                        notificationAdapter.submitData(sorted)
                        getViewBinding().rvNotification.adapter = notificationAdapter
                    } else {
                        getViewBinding().emptyNotif.visibility = View.VISIBLE
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    if(it.message!!.contains("403")){
                        AlertDialog.Builder(context)
                            .setTitle(getString(R.string.warning))
                            .setMessage(getString(R.string.your_session))
                            .setPositiveButton(getString(R.string.login)) { dialogP, _ ->
                                dialogP.dismiss()
                                findNavController().navigate(R.id.action_notificationFragment_to_loginFragment3)
                            }
                            .setNegativeButton(getString(R.string.later)) { negativeButton, _ ->
                                negativeButton.dismiss()
                                findNavController().popBackStack()
                            }
                            .setCancelable(false)
                            .show()
                    }
                }
            }
        }
    }

    override fun setOnClickListeners() {

    }


}
