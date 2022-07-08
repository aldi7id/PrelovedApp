package com.preloved.app.ui.fragment.homepage.sale

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.preloved.app.data.network.model.response.SellerOrderResponse
import com.preloved.app.data.network.model.response.SellerProductResponseItem
import com.preloved.app.databinding.ItemDiminatiBinding
import com.preloved.app.ui.convertDate
import com.preloved.app.ui.currency
import com.preloved.app.ui.striketroughtText

class SaleOrderAdapter(private val OnItemClick: OnClickListener) :
    RecyclerView.Adapter<SaleOrderAdapter.ViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<SellerOrderResponse>() {
        override fun areItemsTheSame(
            oldItem: SellerOrderResponse,
            newItem: SellerOrderResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SellerOrderResponse,
            newItem: SellerOrderResponse
        ): Boolean {
            return oldItem.id == oldItem.id
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitData(value: List<SellerOrderResponse>?) = differ.submitList(value)
    inner class ViewHolder(private val binding: ItemDiminatiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SellerOrderResponse) {
            val basePrice = currency(data.product.basePrice)
            val priceNego = currency(data.price)
            val date = convertDate(data.createdAt)
            binding.apply {
                Glide.with(binding.root)
                    .load(data.product.imageUrl)
                    .transform(CenterCrop(), RoundedCorners(12))
                    .into(binding.ivProductImage)
                tvNamaProduk.text = data.product.name
                tvHargaAwalProduk.text = basePrice
                tvHargaDitawarProduk.text = "Ditawar $priceNego"
                tvTanggal.text = date
                if (data.status != "declined") {
                    root.setOnClickListener {
                        OnItemClick.onClickItem(data)
                    }
                }
                if (data.status == "declined") {
                    root.alpha = 0.5f
                    tvHargaDitawarProduk.apply {
                        text = striketroughtText(this,priceNego)
                    }
                }
            }
        }
    }
    interface OnClickListener {
        fun onClickItem(data: SellerOrderResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemDiminatiBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}
