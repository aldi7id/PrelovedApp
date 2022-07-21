
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.preloved.app.R
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.data.network.model.response.RequestApproveOrder
import com.preloved.app.databinding.FragmentBottomSheetBuyerInfoBinding
import com.preloved.app.databinding.FragmentBottomSheetBuyerInfoStatusBinding
import com.preloved.app.ui.fragment.homepage.buyer.info.BuyerInfoViewModel
import com.preloved.app.ui.fragment.homepage.sale.SaleFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class BottomSheetBuyerInfoStatusFragment(private val idProduct: Int) : BottomSheetDialogFragment() {
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
                        findNavController().navigate(R.id.action_saleFragment_to_loginFragment3)
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
                }
                rbCancel.setOnClickListener {
                    btnSayaTertarikNego.setBackgroundColor(Color.parseColor("#EC698F"))
                    status = "declined"
                }
                btnSayaTertarikNego.setOnClickListener {
                    when (status) {
                        "" -> {
                            Toast.makeText(requireContext(), "Pilih Status Dlu", Toast.LENGTH_SHORT)
                                .show()
                        }
                        "accepted" -> {
                            Toast.makeText(requireContext(), "Berhasil Terjual", Toast.LENGTH_SHORT)
                                .show()
                            val body = RequestApproveOrder(
                                status
                            )
                            viewModel.statusOrder(token, idProduct, body)
                        }
                        "declined" -> {
                            Toast.makeText(requireContext(), "Kamu Nolak", Toast.LENGTH_SHORT)
                                .show()
                            val body = RequestApproveOrder(
                                status
                            )
                            viewModel.statusOrder(token, idProduct, body)
                        }
                    }
                }

            }
        viewModel.statusOrderResult().observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    Toast.makeText(context, "Produk Berhasil Di ${status}", Toast.LENGTH_SHORT).show()
//                    if (response.data?.status == "accepted"){
//                        binding.rbSold.performClick()
//                    }
                }
                is Resource.Error -> {
                }
            }
        }
        }
    }