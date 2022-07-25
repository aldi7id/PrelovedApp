import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.preloved.app.R
import com.preloved.app.databinding.FragmentBottomSheetBuyerInfoBinding
import com.preloved.app.ui.currency
import com.preloved.app.ui.striketroughtText

class BottomSheetBuyerInfoFragment (
    private  val namaPenawar: String,
    private  val kotaPenawar: String,
    private  val imagePenawar: String?,
    private  val productName: String,
    private  val productPrice: String,
    private  val productBid : String,
    private  val imageProduct: String
        ): BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBuyerInfoBinding? = null
    private val binding: FragmentBottomSheetBuyerInfoBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetBuyerInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvNamaPembeli.text = namaPenawar
            tvKotaPembeli.text = kotaPenawar
            if(imagePenawar != null ){
                Glide.with(requireContext())
                    .load(imagePenawar)
                    .placeholder(R.drawable.image_profile)
                    .centerCrop()
                    .transform(CenterCrop(), RoundedCorners(12))
                    .into(ivAvatarPembeli)
            } else {
                ivAvatarPembeli.drawable.apply {
                    R.drawable.image_profile
                }
            }


            tvNamaProduk.text = productName
            tvHargaSeller.apply {
                text = striketroughtText(this, currency(productPrice.toInt()))
            }
            tvHargaTawar.text = "Offered ${currency(productBid.toInt())}"
            Glide.with(requireContext())
                .load(imageProduct)
                .transform(CenterCrop(), RoundedCorners(12))
                .into(ivFotoProduk)
        }
    }

}