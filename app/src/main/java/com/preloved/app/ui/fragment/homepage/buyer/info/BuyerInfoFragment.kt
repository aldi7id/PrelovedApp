import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.data.network.model.response.RequestApproveOrder
import com.preloved.app.data.network.model.response.SellerOrderResponse
import com.preloved.app.databinding.FragmentBuyerInfoBinding
import com.preloved.app.ui.currency
import com.preloved.app.ui.fragment.homepage.buyer.info.BuyerInfoContract
import com.preloved.app.ui.fragment.homepage.buyer.info.BuyerInfoViewModel
import com.preloved.app.ui.fragment.homepage.sale.SaleFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class BuyerInfoFragment : BaseFragment<FragmentBuyerInfoBinding, BuyerInfoViewModel>(
    FragmentBuyerInfoBinding::inflate
) , BuyerInfoContract.View {
    override val viewModel: BuyerInfoViewModel by viewModel()
    private var token = ""
    private lateinit var status: String
    private lateinit var namaPenawar: String
    private lateinit var kotaPenawar: String
    private lateinit var imagePenawar: String
    private lateinit var productName: String
    private lateinit var productPrice: String
    private lateinit var productBid : String
    private lateinit var imageProduct: String


    override fun initView() {
        viewModel.userSession()
        setOnClickListeners()
    }

    override fun setOnClickListeners() {
        getViewBinding().apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnTerima.setOnClickListener {
                val bundle = arguments
                val idOrder = bundle?.getInt(SaleFragment.ORDER_ID)
                AlertDialog.Builder(requireContext())
                    .setTitle("Pesan")
                    .setMessage("Terima Tawaran?")
                    .setPositiveButton("Iya"){ positive, _ ->
                        status = "accepted"
                        val body = RequestApproveOrder(
                            status
                        )
                        if (token != null && idOrder != null) {
                            viewModel.statusOrder(token, idOrder, body)
                            positive.dismiss()
                        }

                    }
                    .setNegativeButton("Tidak"){ negative, _ ->
                        negative.dismiss()
                    }
                    .show()
            }
            btnTolak.setOnClickListener {
                val bundle = arguments
                val idOrder = bundle?.getInt(SaleFragment.ORDER_ID)
                AlertDialog.Builder(requireContext())
                    .setTitle("Pesan")
                    .setMessage("Tolak Tawaran?")
                    .setPositiveButton("Iya"){ positive, _ ->
                        status = "declined"
                        val body = RequestApproveOrder(
                            status
                        )
                        if (token != null && idOrder != null) {
                            viewModel.statusOrder(token, idOrder, body)
                            positive.dismiss()
                        }
                    }
                    .setNegativeButton("Tidak"){ negative, _ ->
                        negative.dismiss()
                    }
                    .show()
            }
            btnHubungi.setOnClickListener {
                val bottomFragment = BottomSheetBuyerInfoFragment(
                    namaPenawar, kotaPenawar, imagePenawar, productName, productPrice, productBid, imageProduct
                )
                bottomFragment.show(parentFragmentManager, "Tag")
            }
            btnStatus.setOnClickListener {
                //StatusBottomNav
            }
        }
    }

    override fun showLoading(isVisible: Boolean) {
        super.showLoading(isVisible)
        getViewBinding().pbLoading.isVisible = isVisible
    }

    override fun showContent(isVisible: Boolean) {
        super.showContent(isVisible)
        getViewBinding().groupContent.isVisible = isVisible
    }

    override fun setDataToView(data: SellerOrderResponse) {
        getViewBinding().apply {
            tvNamaPenawar.text = data.user.fullName
            tvKotaPenawar.text = data.user.city.toString()
            Glide.with(requireContext())
                .load(data.user.imageUrl)
                .placeholder(R.drawable.image_profile)
                .into(getViewBinding().ivAvatarPenawar)
            tvNamaProduk.text = data.product.name
            Glide.with(requireContext())
                .load(data.product.imageUrl)
                .placeholder(R.drawable.image_profile)
                .into(getViewBinding().ivProductImage)
            tvHargaAwalProduk.text = currency(data.product.basePrice)
            tvHargaDitawarProduk.text = currency(data.price)
            namaPenawar = data.user.fullName
            imagePenawar = data.user.imageUrl
            productName = data.productName
            productPrice = data.basePrice
            productBid = data.price.toString()
            kotaPenawar = data.user.city
            imageProduct = data.product.imageUrl

            if(data.status == "accepted"){
                btnGroup.visibility = View.GONE
                btnGroupAccepted.visibility = View.VISIBLE
                val bottomFragment = BottomSheetBuyerInfoFragment(
                    namaPenawar, kotaPenawar, imagePenawar, productName, productPrice, productBid, imageProduct
                )
                bottomFragment.show(parentFragmentManager, "Tag")
            }
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.userSessionResult().observe(viewLifecycleOwner) {
            if(it.access_token == DatastoreManager.DEFAULT_ACCESS_TOKEN){
                AlertDialog.Builder(context)
                    .setTitle(getString(R.string.warning))
                    .setMessage(getString(R.string.please_login))
                    .setPositiveButton(R.string.login) { dialogP, _ ->
                        //ToLogin Fragment
                        findNavController().navigate(R.id.action_accountFragment_to_loginFragment3)
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

                val bundle = arguments
                val idOrderBuyer = bundle?.getInt(SaleFragment.ORDER_ID)
                viewModel.getSellerOrderById(token, idOrderBuyer!!)
            }
        }
        viewModel.getSellerOrderByIdResult().observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    response.data?.let { setDataToView(it) }
                }
                is Resource.Error -> {
                    showLoading(true)
                    showContent(false)
                }
            }
        }
        viewModel.statusOrderResult().observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    Toast.makeText(context, "Produk Berhasil Di ${status}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    showLoading(true)
                }
            }
        }
    }
}
