
import android.app.ActionBar
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.preloved.app.R
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.data.network.model.response.RequestApproveOrder
import com.preloved.app.databinding.FragmentBottomSheetBuyerInfoStatusBinding
import com.preloved.app.ui.fragment.homepage.buyer.info.BuyerInfoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetBuyerInfoStatusFragment(private val idOrder: Int) : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetBuyerInfoStatusBinding? = null
    private val binding: FragmentBottomSheetBuyerInfoStatusBinding get() = _binding!!
    private var status: String = ""
    private var token = ""
    private val viewModel: BuyerInfoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetBuyerInfoStatusBinding.inflate(inflater, container, false)
        viewModel.userSession()
        viewModel.userSessionResult().observe(viewLifecycleOwner) {
            if (it.access_token == DatastoreManager.DEFAULT_ACCESS_TOKEN) {
                AlertDialog.Builder(context)
                    .setTitle(getString(R.string.warning))
                    .setMessage(getString(R.string.please_login))
                    .setPositiveButton(getString(R.string.login)) { dialogP, _ ->
                        //ToLogin Fragment
                        dialogP.dismiss()
                        findNavController().navigate(R.id.action_saleFragment_to_loginFragment3)

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
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            binding.apply {
                rbSold.setOnClickListener {
                    btnSayaTertarikNego.setBackgroundColor(Color.parseColor("#EC698F"))
                    status = "accepted"
                    Log.d("HAYOO", status)
                }
                rbCancel.setOnClickListener {
                    btnSayaTertarikNego.setBackgroundColor(Color.parseColor("#EC698F"))
                    status = "declined"
                    Log.d("HAYOO", status)
                }
                btnSayaTertarikNego.setOnClickListener {
                    when (status) {
                        "" -> {
                            Toast.makeText(requireContext(), getString(R.string.choose_status), Toast.LENGTH_SHORT)
                                .show()
                        }
                        "accepted" -> {
                            val body = RequestApproveOrder(
                                status
                            )
                            viewModel.statusProcutAccepted(token, idOrder, body)
                            Toast.makeText(context, "Accept Clicked", Toast.LENGTH_SHORT).show()
                            Log.d("HAYOO", idOrder.toString())
                        }
                        "declined" -> {
                            val body = RequestApproveOrder(
                                status
                            )
                            viewModel.statusProcutAccepted(token, idOrder, body)
                            Toast.makeText(context, "Declined Clicked", Toast.LENGTH_SHORT).show()
                            Log.d("HAYOO", idOrder.toString())
                        }
                    }
                }

            }
        viewModel.statusProcutAcceptedResult().observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    if(status == "accepted"){
                        Toast.makeText(requireContext(), "Product Accepted", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                        dismiss()
                    } else {
                        findNavController().popBackStack()
                        Toast.makeText(requireContext(), "Product Canceled", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                }
                is Resource.Error -> {
                }
            }
        }
        Log.d("HAYO", status)
        }
    }