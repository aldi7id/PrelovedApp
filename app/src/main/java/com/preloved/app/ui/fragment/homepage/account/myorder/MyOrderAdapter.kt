package com.preloved.app.ui.fragment.homepage.account.myorder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.preloved.app.data.network.model.BuyerOrderResponse
import com.preloved.app.data.network.model.response.SellerProductResponseItem
import com.preloved.app.databinding.ItemMyOrderBinding
import com.preloved.app.databinding.ItemSelledBinding
import com.preloved.app.ui.convertDate
import com.preloved.app.ui.currency
import com.preloved.app.ui.fragment.homepage.sale.SaleAcceptedAdapter
import com.preloved.app.ui.striketroughtText

class MyOrderAdapter(private val OnItemClick: OnClickListener):RecyclerView.Adapter<MyOrderAdapter.ViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<BuyerOrderResponse>() {
        override fun areItemsTheSame(
            oldItem: BuyerOrderResponse,
            newItem: BuyerOrderResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BuyerOrderResponse,
            newItem: BuyerOrderResponse
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitData(value: List<BuyerOrderResponse>?) = differ.submitList(value)
    inner class ViewHolder(private val binding: ItemMyOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BuyerOrderResponse) {
           binding.apply {
               tvNamaProduk.text = data.productName
               tvHargaAwalProduk.text = currency(data.basePrice.toInt())
               tvHargaDitawarProduk.text = currency(data.price)
               Glide.with(binding.root)
                   .load(data.imageProduct)
                   .transform(CenterCrop(), RoundedCorners(12))
                   .into(binding.ivProductImage)
               tvTanggal.text = convertDate(data.transactionDate.toString())
               tvTipeProduk.text = data.status
           }
        }
    }
    interface OnClickListener {
        fun onClickItem(data: BuyerOrderResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemMyOrderBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}