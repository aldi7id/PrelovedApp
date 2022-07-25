package com.preloved.app.ui.fragment.homepage.sale

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.preloved.app.data.network.model.HistoryResponseItem
import com.preloved.app.databinding.ItemHistoryBinding
import com.preloved.app.ui.convertDate
import com.preloved.app.ui.currency
import com.preloved.app.ui.striketroughtText

class SaleHistoryAdapter(private val OnItemClick: OnClickListener) : RecyclerView.Adapter<SaleHistoryAdapter.ViewHolder>() {
    private val diffCallback = object  : DiffUtil.ItemCallback<HistoryResponseItem>() {
        override fun areItemsTheSame(
            oldItem: HistoryResponseItem,
            newItem: HistoryResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: HistoryResponseItem,
            newItem: HistoryResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitData(value: List<HistoryResponseItem>?) = differ.submitList(value)
    inner class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HistoryResponseItem) {
            if(data.product != null && data.status == "accepted"){
                val basePrice = currency(data.product.basePrice)
                val priceNego = currency(data.price)
                val date = convertDate(data.createdAt)
                binding.apply {
                    Glide.with(binding.root)
                        .load(data.imageUrl)
                        .transform(CenterCrop(), RoundedCorners(12))
                        .into(binding.ivProductImage)
                    tvNamaProduk.text = data.productName
                    tvHargaAwalProduk.apply {
                        text = striketroughtText(this,basePrice)
                    }
                    tvHargaDitawarProduk.text = "Selled $priceNego"
                    tvTanggal.text = date
                    if (data.status == "accepted") {
                        root.setOnClickListener {
                            OnItemClick.onClickItem(data)
                        }
                    }
//                if (data.status == "declined") {
//                    root.alpha = 0.5f
//                    tvHargaDitawarProduk.apply {
//                        text = striketroughtText(this,priceNego)
//                    }
//                }
                }
            } else {
                binding.apply {
                    tvHargaAwalProduk.text = "Product Has Been Deleted"
                }
            }

        }
    }
    interface OnClickListener {
        fun onClickItem(data: HistoryResponseItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemHistoryBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}