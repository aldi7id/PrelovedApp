package com.preloved.app.ui.fragment.homepage.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.preloved.app.data.network.model.response.NotificationResponse
import com.preloved.app.databinding.ItemNotificationBinding
import com.preloved.app.ui.convertDate
import com.preloved.app.ui.currency
import com.preloved.app.ui.striketroughtText

class NotificationAdapter(
    private val onItemClick : OnClickListener
) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>(){
    private val diffCallBack = object : DiffUtil.ItemCallback<NotificationResponse>(){
        override fun areItemsTheSame(
            oldItem: NotificationResponse,
            newItem: NotificationResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NotificationResponse,
            newItem: NotificationResponse
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value:List<NotificationResponse>?) = differ.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemNotificationBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: ItemNotificationBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(data: NotificationResponse){
                binding.apply {
                    when (data.status) {
                        "bid" -> {
                            if (data.product != null){
                                tvHargaAwalProduk.apply {
                                    text = striketroughtText(this, currency(data.basePrice.toInt()))
                                }
                                if(data.receiverId == data.product.userId){
                                    tvPesan.text = "Someone bid on your product"
                                    if(data.notificationType == "seller"){
                                        root.setOnClickListener{
                                            onItemClick.onClickItemInfo(data)
                                        }
                                    }
                                    if (data.product.status == "sold"){
                                        tvTipeProduk.text = "Product Accepted"
                                        tvPesan.text = "You accept this offer"
                                    }
                                } else {
                                    tvPesan.text = "Your offer has not been accepted by the seller, be patient!"
                                }
                            } else {
                                tvPesan.text = "Product Already Delete By Seller"
                            }
//                            root.setOnClickListener{
//                                onItemClick.onClickItem(data)
//                            }
                        }
                        "declined" -> {
                            tvHargaAwalProduk.apply {
                                text = striketroughtText(this, currency(data.basePrice.toInt()))
                            }
                            tvTipeProduk.text = "Product Declined"
                            if (data.product != null){
                                if (data.receiverId == data.product.userId){
                                    tvPesan.text = "You decline this offer"
                                } else {
                                    tvPesan.text = "Your offer was declined by the Seller"
                                }
                            } else {
                                tvPesan.text = "Product Already Delete By Seller"
                            }
                            root.setOnClickListener{
                                onItemClick.onClickItem(data)
                            }
                        }
                        "accepted" -> {
                            tvHargaAwalProduk.apply {
                                text = striketroughtText(this, currency(data.basePrice.toInt()))
                            }
                            tvTipeProduk.text = "Product Accepted"
                            if (data.product != null){
                                if (data.receiverId == data.product.userId){
                                    tvPesan.text = "You accept this product"
                                } else {
                                    tvPesan.text = "Your offer is accepted by the Seller"
                                }
                            } else {
                                tvPesan.text = "Product Already Delete By Seller"
                            }
                            root.setOnClickListener{
                                onItemClick.onClickItem(data)
                            }
                        }
                        "create" -> {
                            tvTipeProduk.text = "Product Add"
                            tvPesan.text = "Your Product Successfully Added"
                            tvHargaDitawarProduk.visibility = View.GONE
                            tvHargaAwalProduk.text = currency(data.basePrice.toInt())
                            root.setOnClickListener{
                                onItemClick.onClickItemSell(data)
                            }
                        }
//                        else -> {

//                        }
                    }
                    tvHargaDitawarProduk.text =
                        if (data.status == "declined") "Declined " + currency(data.bidPrice)
                        else if(data.status == "accepted") "Accepted " + currency(data.bidPrice)
                        else if(data.status == "bid") "Offer " + currency(data.bidPrice)
                        else ""
                    tvNamaProduk.text = data.productName
//                    tvHargaAwalProduk.apply {
//                        text = striketroughtText(this, currency(data.basePrice.toInt()))
//                    }
                    tvTanggal.text = data.transactionDate?.let { convertDate(it) }
                    if (!data.read){
                        Glide.with(binding.root)
                            .load(data.imageUrl)
                            .centerCrop()
                            .into(ivProductImage)
                    }
                }
            }
        }


    interface OnClickListener{
        fun onClickItem(data: NotificationResponse)
        fun onClickItemSell(data: NotificationResponse)
        fun onClickItemInfo(data: NotificationResponse)
    }
}