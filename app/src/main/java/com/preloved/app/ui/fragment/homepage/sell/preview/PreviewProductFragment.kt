import android.app.ActionBar
import android.app.AlertDialog
import android.net.Uri
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.preloved.app.R
import com.preloved.app.base.arch.BaseFragment
import com.preloved.app.databinding.FragmentPreviewProductBinding
import com.preloved.app.ui.fragment.homepage.account.AccountFragment
import com.preloved.app.ui.fragment.homepage.home.HomeFragment
import com.preloved.app.ui.fragment.homepage.sell.SellFragment.Companion.CATEGORY_PRODUCT
import com.preloved.app.ui.fragment.homepage.sell.SellFragment.Companion.CITY_USER
import com.preloved.app.ui.fragment.homepage.sell.SellFragment.Companion.DESC_PRODUCT
import com.preloved.app.ui.fragment.homepage.sell.SellFragment.Companion.IMAGE_PRODUCT
import com.preloved.app.ui.fragment.homepage.sell.SellFragment.Companion.IMAGE_USER
import com.preloved.app.ui.fragment.homepage.sell.SellFragment.Companion.NAME_USER
import com.preloved.app.ui.fragment.homepage.sell.SellFragment.Companion.PRICE_PRODUCT
import com.preloved.app.ui.fragment.homepage.sell.SellFragment.Companion.TITLE_PRODUCT
import com.preloved.app.ui.fragment.homepage.sell.SellFragment.Companion.USER_TOKEN
import com.preloved.app.ui.fragment.homepage.sell.preview.PreviewProductContract
import com.preloved.app.ui.fragment.homepage.sell.preview.PreviewProductViewModel
import com.preloved.app.ui.listCategoryId
import com.preloved.app.ui.uriToFile
import org.koin.androidx.viewmodel.ext.android.viewModel

class PreviewProductFragment : BaseFragment<FragmentPreviewProductBinding, PreviewProductViewModel>(
    FragmentPreviewProductBinding::inflate
) , PreviewProductContract.View {
    override val viewModel: PreviewProductViewModel by viewModel()

    override fun initView() {
        getViewBinding().statusBar.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, HomeFragment.result
        )
        getViewBinding().apply {
            val bundle = arguments
            val token = bundle?.getString(USER_TOKEN)
            val productName = bundle?.getString(TITLE_PRODUCT)
            val priceProduct = bundle?.getString(PRICE_PRODUCT)
            val descProduct = bundle?.getString(DESC_PRODUCT)
            val sellerName = bundle?.getString(NAME_USER)
            val cityName = bundle?.getString(CITY_USER)
            val userImage = bundle?.getString(IMAGE_USER)
            val categoryProduct = bundle?.getString(CATEGORY_PRODUCT)
            val imageProduct = bundle?.getString(IMAGE_PRODUCT)
            Log.d("token edit", token.toString())
            tvProdukName.text = productName
            tvProdukHarga.text = priceProduct
            tvDeskripsiProduk.text = descProduct
            tvNamaPenjual.text = sellerName
            tvKotaPenjual.text = cityName
            tvProdukKategori.text = categoryProduct
            Glide.with(requireContext())
                .load(userImage)
                .transform(CenterCrop(), RoundedCorners(12))
                .into(ivAvatarPenjual)
            Glide.with(requireContext())
                .load(imageProduct)
                .centerCrop()
                .into(getViewBinding().imageProduk)

        }
        setOnClickListeners()
    }


    override fun setOnClickListeners() {
        getViewBinding().apply {
            btnBack.setOnClickListener {

            }
            btnTerbitkan.setOnClickListener {
                val bundle = arguments
                val token = bundle?.getString(USER_TOKEN)
                val imageProduct = bundle?.getString(IMAGE_PRODUCT)
                AlertDialog.Builder(requireContext())
                    .setTitle("Pesan")
                    .setMessage("Terbitkan Produk?")
                    .setPositiveButton("Iya") { positiveButton, _ ->
                        if (token != null) {
                            viewModel.postProductData(
                                token,
                                name = tvProdukName.text.toString(),
                                category = listCategoryId,
                                base_price = tvProdukHarga.text.toString().toInt(),
                                description = tvDeskripsiProduk.text.toString(),
                                location = tvKotaPenjual.text.toString(),
                                image = uriToFile(Uri.parse(imageProduct),requireContext())
                                )
                        }
                        positiveButton.dismiss()
                    }
                    .setNegativeButton("Batal") { negativeButton, _ ->
                        negativeButton.dismiss()
                    }
                    .show()
            }
        }

    }

    override fun observeData() {
        super.observeData()
    }
    private fun showToastSuccess() {
        val snackBarView =
            Snackbar.make(getViewBinding().root, "Produk berhasil di terbitkan.", Snackbar.LENGTH_LONG)
        val layoutParams = ActionBar.LayoutParams(snackBarView.view.layoutParams)
        snackBarView.setAction(" ") {
            snackBarView.dismiss()
        }
        val textView =
            snackBarView.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_add, 0)
        textView.compoundDrawablePadding = 16
        layoutParams.gravity = Gravity.TOP
        layoutParams.setMargins(32, 150, 32, 0)
        snackBarView.view.setPadding(24, 16, 0, 16)
        snackBarView.view.setBackgroundColor(resources.getColor(R.color.primary))
        snackBarView.view.layoutParams = layoutParams
        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()
    }

}