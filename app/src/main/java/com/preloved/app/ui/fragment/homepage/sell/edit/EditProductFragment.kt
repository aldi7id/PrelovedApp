import android.app.AlertDialog
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
import com.preloved.app.data.network.model.response.PostProductResponse
import com.preloved.app.data.network.model.response.SellerProductResponseItem
import com.preloved.app.data.network.model.response.UserResponse
import com.preloved.app.databinding.FragmentEditProductBinding
import com.preloved.app.ui.currency
import com.preloved.app.ui.fragment.homepage.sale.SaleFragment
import com.preloved.app.ui.fragment.homepage.sale.SaleFragment.Companion.PRODUCT_ID
import com.preloved.app.ui.fragment.homepage.sell.edit.EditProductContract
import com.preloved.app.ui.fragment.homepage.sell.edit.EditProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class EditProductFragment : BaseFragment<FragmentEditProductBinding, EditProductViewModel>(
    FragmentEditProductBinding::inflate
) , EditProductContract.View {
    override val viewModel: EditProductViewModel by viewModel()
    private var selectedPicture: File? = null
    private var token = ""


    override fun initView() {
        viewModel.userSession()
    }

    override fun setOnClickListeners() {

    }

    override fun observeData() {
        super.observeData()
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
            } else {
                token = it.access_token

                val bundle = arguments
                var idProduct =  bundle?.getInt(PRODUCT_ID)
                viewModel.getSellerProduct(token, idProduct!!)
                Log.d("DIDIDI", bundle.toString())

            }
        }
        viewModel.getSellerProductResult().observe(viewLifecycleOwner){ response ->
            when(response){
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
                    showLoading(false)
                    showContent(true)
                    showError(true, response.message)
                }
            }
        }
        viewModel.updateResultProductData().observe(viewLifecycleOwner){
                response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    Toast.makeText(requireContext(), "Edit Product Success!", Toast.LENGTH_SHORT).show()
                    response.data?.let { setDataToViewChange(it) }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(true)
                    showError(true, response.message)
                }
            }
        }
    }
    override fun showLoading(isVisible: Boolean) {
        super.showLoading(isVisible)
        getViewBinding().pbLoading.isVisible = isVisible
    }

    override fun showContent(isVisible: Boolean) {
        super.showContent(isVisible)
        //getViewBinding().groupContent.isVisible = isVisible
    }
    override fun setDataToView(data: SellerProductResponseItem) {
        getViewBinding().apply {
            etNamaProduk.setText(data.name)
            etHargaProduk.setText(currency(data.basePrice).drop(3))
            etDeskripsi.setText(data.description)
            Glide.with(requireContext())
                .load(data.imageUrl)
                .placeholder(R.drawable.ic_add_product_new)
                .into(getViewBinding().ibAddImage)
            etKategory.setText(data.categories[0].name)
        }
    }
    override fun setDataToViewChange(data: PostProductResponse) {
        getViewBinding().apply {
//            etEmail.setText(data)
//            etNama.setText(data.fullName)
//            etCity.setText(data.city)
//            etAddress.setText(data.address)
//            etPhone.setText(data.phoneNumber)
//            Glide.with(requireContext())
//                .load(data.imageUrl)
//                .transform(CenterCrop(), RoundedCorners(12))
//                .placeholder(R.drawable.ic_profile)
//                .into(getViewBinding().ivProfile)
////            tfAddress.error = null
////            tfNama.error = null
////            tfPhone.error = null
////            tfCity.error = null
        }
    }

}