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
                        dialogP.dismiss()
                        val passdata = NotificationFragmentDirections.actionNotificationFragmentToLoginFragment3(
                            status = 1
                        )
                        findNavController().navigate(passdata)
                        viewModel.userSessionResult().removeObservers(viewLifecycleOwner)
                    }
                    .setNegativeButton(getString(R.string.later)) { dialogN, _ ->
                        //ToHomeFragment
                        dialogN.dismiss()
                        findNavController().navigate(R.id.homeFragment)

                    }
                    .setCancelable(false)
                    .show()
            } else {
                token = it.access_token
                Log.d ("token", token)
                viewModel.getNotification(token)
            }
            viewModel.userSessionResult().removeObservers(viewLifecycleOwner)
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
                                   val passdata = NotificationFragmentDirections.actionNotificationFragmentToDetailProductFragment2(
                                       productId = data.productId
                                   )
                                    findNavController().navigate(passdata)
                                }
                                override fun onClickItemSell(data: NotificationResponse) {
                                    val passdata = NotificationFragmentDirections.actionNotificationFragmentToEditProductFragment(
                                        productId = data.productId
                                    )
                                    findNavController().navigate(passdata)
                                }

                                override fun onClickItemInfo(data: NotificationResponse) {
                                    val passdata = NotificationFragmentDirections.actionNotificationFragmentToBuyerInfoFragment(
                                        productId = data.productId,
                                        orderId = data.orderId
                                    )
                                    findNavController().navigate(passdata)
                                }
                            })
                        val sorted = it.data.sortedByDescending { it.id }
                        notificationAdapter.submitData(sorted)
                        getViewBinding().rvNotification.adapter = notificationAdapter
                    } else {
                        getViewBinding().lottieEmpty.visibility = View.VISIBLE
                        getViewBinding().tvLottieEmpty.visibility = View.VISIBLE
                    }
                    viewModel.getNotificationResult().removeObservers(viewLifecycleOwner)
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
