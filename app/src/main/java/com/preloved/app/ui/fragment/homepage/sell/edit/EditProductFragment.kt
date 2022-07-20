import android.app.ActionBar
import android.app.Activity
import android.app.AlertDialog
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.base.model.Resource
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.data.network.model.response.PostProductResponse
import com.preloved.app.data.network.model.response.SellerProductResponseItem
import com.preloved.app.databinding.FragmentEditProductBinding
import com.preloved.app.ui.fragment.homepage.sale.SaleFragment.Companion.PRODUCT_ID
import com.preloved.app.ui.fragment.homepage.sell.BottomSheetChooseCategoryFragment
import com.preloved.app.ui.fragment.homepage.sell.edit.EditProductContract
import com.preloved.app.ui.fragment.homepage.sell.edit.EditProductViewModel
import com.preloved.app.ui.listCategory
import com.preloved.app.ui.listCategoryId
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class EditProductFragment : BaseFragment<FragmentEditProductBinding, EditProductViewModel>(
    FragmentEditProductBinding::inflate
) , EditProductContract.View {
    override val viewModel: EditProductViewModel by viewModel()
    private var token = ""
    private var selectedPicture: File? = null
    private var location = ""


    override fun initView() {
        viewModel.userSession()
        viewModel.getCategoryData()
        setOnClickListeners()
    }

    override fun setOnClickListeners() {
        getViewBinding().apply {
            etKategory.setOnClickListener {
                val bottomFragment = BottomSheetChooseCategoryFragment(
                    update = {
                        viewModel.addCategory(listCategory)
                    })
                bottomFragment.show(parentFragmentManager, "Tag")
            }
            ibAddImage.setOnClickListener{
                ImagePicker.with(this@EditProductFragment)
                    .crop()
                    .saveDir(
                        File(
                            getActivity()?.getExternalCacheDir(),
                            "ImagePicker"
                        )
                    )
                    .compress(1024)
                    .maxResultSize(1080,1080)
                    .createIntent {
                        startForProfileImageResult.launch(it)
                    }
            }
            btnEdit.setOnClickListener {

                if (checkFormValidation()){
                    val productName = etNamaProduk.text.toString()
                    val productPrice = etHargaProduk.text.toString().toInt()
                    val productDesc = etDeskripsi.text.toString()
                    val bundle = arguments
                    val idProduct =  bundle?.getInt(PRODUCT_ID)
                    Log.d("HAYO", bundle.toString())
                    if (idProduct != null) {
                        viewModel.updateProductData(
                            token = token,
                            id = idProduct,
                            name = productName,
                            base_price = productPrice,
                            category = listCategoryId,
                            description = productDesc,
                            location = location,
                            image = selectedPicture
                        )
                    }
                }
                listCategory.clear()
            }
            btnDelete.setOnClickListener {
                val bundle = arguments
                val idProduct =  bundle?.getInt(PRODUCT_ID)
                AlertDialog.Builder(context)
                    .setTitle(getString(R.string.warning))
                    .setMessage(getString(R.string.message_want_delete_product))
                    .setPositiveButton(getString(R.string.sure)) { dialogP, _ ->
                        //ToLogin Fragment
                        if (idProduct != null) {
                            viewModel.deleteProductSeller(token,idProduct)
                        }
                        findNavController().navigate(R.id.action_editProductFragment_to_saleFragment)
                        showToastSuccessDelete()
                        dialogP.dismiss()
                        listCategory.clear()
                    }
                    .setNegativeButton(getString(R.string.cancel)) { dialogN, _ ->
                        //ToHomeFragment
                        dialogN.dismiss()
                    }
                    .setCancelable(false)
                    .show()

            }
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.categoryList.observe(viewLifecycleOwner) { kat ->
            if (kat.isNotEmpty()) {
                var kategori = ""
                for (element in kat) {
                    kategori += ", $element"
                }
                getViewBinding().etKategory.setText(kategori.drop(2))

            } else
                getViewBinding().etKategory.setText(getString(R.string.choose_category))
        }
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
                val idProduct =  bundle?.getInt(PRODUCT_ID)
                viewModel.getSellerProduct(token, idProduct!!)

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
                    findNavController().navigate(R.id.action_editProductFragment_to_saleFragment)
                    showToastSuccess()
//                    Toast.makeText(requireContext(), "Edit Product Success!", Toast.LENGTH_SHORT).show()
//                    response.data?.let { setDataToViewChange(it) }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(true)
                    showError(true, response.message)
                }
            }
        }
    }
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when(resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    Glide.with(requireContext()).load(fileUri).into(getViewBinding().ibAddImage)
                    fileUri.path?.let {
                        val file = File(it)
                        if (file.exists()) {
                            selectedPicture = file
                        }
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), getString(R.string.task_cancelled), Toast.LENGTH_SHORT).show()
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
            location = data.location
            etNamaProduk.setText(data.name)
            etHargaProduk.setText(data.basePrice.toString())
            etDeskripsi.setText(data.description)
            Glide.with(requireContext())
                .load(data.imageUrl)
                .placeholder(R.drawable.ic_add_product_new)
                .into(getViewBinding().ibAddImage)
            var listCategory1 = ""

            for (kategori in data.categories){
                listCategory1 += ", ${kategori.name}"
                //listCategoryId += ", ${kategori.id}"
                listCategoryId.add(kategori.id)
                listCategory.add(kategori.name)
            }
            etKategory.setText(listCategory1.drop(2))

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

    override fun checkFormValidation(): Boolean {
        getViewBinding().apply {
            var isFormValid = true
            val nameProduct = etNamaProduk.text.toString()
            val priceProduct = etHargaProduk.text.toString()
            val descProduct = etDeskripsi.text.toString()
            when {
                nameProduct.isEmpty() -> {
                    isFormValid = false
                    tfNamaProduk.isErrorEnabled = true
                    tfNamaProduk.error = getString(R.string.enter_name_product)
                }
                priceProduct.isEmpty() -> {
                    isFormValid = false
                    tfHargaProduk.isErrorEnabled = true
                    tfHargaProduk.error = getString(R.string.enter_price_product)
                }
                descProduct.isEmpty() -> {
                    isFormValid = false
                    tfDeksipsi.error = getString(R.string.enter_desc_product)
                }
                else -> {
                tfNamaProduk.isErrorEnabled = false
                    tfHargaProduk.isErrorEnabled = false
                    tfDeksipsi.isErrorEnabled = false
            }
            }
            return isFormValid
        }
    }

    private fun showToastSuccess() {
        val snackBarView =
            Snackbar.make(getViewBinding().root, getString(R.string.message_edit_product), Snackbar.LENGTH_LONG)
        val layoutParams = ActionBar.LayoutParams(snackBarView.view.layoutParams)
        snackBarView.setAction(" ") {
            snackBarView.dismiss()
        }
        val textView =
            snackBarView.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_close, 0)
        textView.compoundDrawablePadding = 16
        layoutParams.gravity = Gravity.TOP
        layoutParams.setMargins(32, 150, 32, 0)
        snackBarView.view.setPadding(24, 16, 0, 16)
        snackBarView.view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
        snackBarView.view.layoutParams = layoutParams
        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()
    }
    private fun showToastSuccessDelete() {
        val snackBarView =
            Snackbar.make(getViewBinding().root, getString(R.string.message_delete_product), Snackbar.LENGTH_LONG)
        val layoutParams = ActionBar.LayoutParams(snackBarView.view.layoutParams)
        snackBarView.setAction(" ") {
            snackBarView.dismiss()
        }
        val textView =
            snackBarView.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_close, 0)
        textView.compoundDrawablePadding = 16
        layoutParams.gravity = Gravity.TOP
        layoutParams.setMargins(32, 150, 32, 0)
        snackBarView.view.setPadding(24, 16, 0, 16)
        snackBarView.view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
        snackBarView.view.layoutParams = layoutParams
        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()
    }
}