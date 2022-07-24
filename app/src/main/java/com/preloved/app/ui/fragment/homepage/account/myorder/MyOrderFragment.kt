import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.data.network.model.BuyerOrderResponse
import com.preloved.app.databinding.FragmentMyOrderBinding
import com.preloved.app.ui.fragment.homepage.account.myorder.MyOrderAdapter
import com.preloved.app.ui.fragment.homepage.account.myorder.MyOrderContract
import com.preloved.app.ui.fragment.homepage.account.myorder.MyOrderViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyOrderFragment : BaseFragment<FragmentMyOrderBinding, MyOrderViewModel>
    (FragmentMyOrderBinding::inflate), MyOrderContract.View {
    companion object {
        const val USER_ORDER = "OrderId"
    }
    override val viewModel: MyOrderViewModel by viewModel()
    override fun initView() {
        viewModel.userSession()
        setOnClickListeners()
    }

    override fun setOnClickListeners() {
        getViewBinding().apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
    override fun showLoading(isVisible: Boolean) {
        super.showLoading(isVisible)
        getViewBinding().pbLoading.isVisible = isVisible
    }
    override fun observeData() {
        super.observeData()
        viewModel.userSessionResult().observe(viewLifecycleOwner){
            if(it.access_token == DatastoreManager.DEFAULT_ACCESS_TOKEN){
                AlertDialog.Builder(context)
                    .setTitle(getString(R.string.warning))
                    .setMessage(getString(R.string.please_login))
                    .setPositiveButton(getString(R.string.login)) { dialogP, _ ->
                        //ToLogin Fragment
                        dialogP.dismiss()
                        findNavController().navigate(R.id.action_accountFragment_to_loginFragment3)

                    }
                    .setNegativeButton(getString(R.string.later)) { dialogN, _ ->
                        //ToHomeFragment
                        dialogN.dismiss()
                        findNavController().navigate(R.id.homeFragment)

                    }
                    .setCancelable(false)
                    .show()
            } else {
                //bundle.putString(AccountFragment.USER_TOKEN,it.access_token)
                viewModel.getBuyerOrder(it.access_token)
            }
        }
        viewModel.getBuyerOrderResult().observe(viewLifecycleOwner){
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    val myOrderAdapter = MyOrderAdapter(object  : MyOrderAdapter.OnClickListener{
                        override fun onClickItem(data: BuyerOrderResponse) {
                            val passData = MyOrderFragmentDirections.actionMyOrderFragmentToDetailProductFragment2(
                                productId = data.productId,
                                status = 1,
                                orderId = data.id
                            )
                            findNavController().navigate(
                                passData
                            )
                        }
                    })
                    val sorted = it.data?.sortedByDescending { it.id }
                    myOrderAdapter.submitData(sorted)
                    getViewBinding().rvMyOrder.adapter = myOrderAdapter
                    if(it.data?.size == 0){
                        getViewBinding().apply {
                            lottieEmpty.visibility = View.VISIBLE
                            tvLottieEmpty.visibility = View.VISIBLE
                        }
                    } else {
                        getViewBinding().apply {
                            lottieEmpty.visibility = View.GONE
                            tvLottieEmpty.visibility = View.GONE
                        }
                    }

                }
                is Resource.Error -> {

                }
            }
        }
    }

}