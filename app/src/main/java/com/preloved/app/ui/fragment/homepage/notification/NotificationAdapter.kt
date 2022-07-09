package com.preloved.app.ui.fragment.homepage.notification

import android.view.LayoutInflater
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
                                if(data.receiverId == data.product.userId){
                                    tvPesan.text = "Ada yang tawar produkmu!"
                                } else {
                                    tvPesan.text = "Tawaranmu belum diterima oleh penjual, sabar ya!"
                                }
                            } else {
                                tvPesan.text = "Produk Sudah di hapus"
                            }
                        }
                        "declined" -> {
                            tvTipeProduk.text = "Produk Ditolak"
                            if (data.product != null){
                                if (data.receiverId == data.product.userId){
                                    tvPesan.text = "Anda menolak Tawaran ini"
                                } else {
                                    tvPesan.text = "Tawaran Anda ditolak oleh Penjual"
                                }
                            } else {
                                tvPesan.text = "Produk Sudah di hapus"
                            }
                        }
                        "accepted" -> {
                            tvTipeProduk.text = "Produk Diterima"
                            if (data.product != null){
                                if (data.receiverId == data.product.userId){
                                    tvPesan.text = "Anda menerima Tawaran ini"
                                } else {
                                    tvPesan.text = "Tawaran Anda diterima oleh Penjual"
                                }
                            } else {
                                tvPesan.text = "Produk Sudah di hapus"
                            }
                        }
                        else -> {
                            tvPesan.text = " "
                        }
                    }
                    tvHargaDitawarProduk.text =
                        if (data.status == "declined") "Ditolak " + currency(data.bidPrice)
                        else if(data.status == "accepted") "Diterima " + currency(data.bidPrice)
                        else "Ditawar " + currency(data.bidPrice)
                    tvNamaProduk.text = data.productName
                    tvHargaAwalProduk.apply {
                        text = striketroughtText(this, currency(data.basePrice.toInt()))
                    }
                    tvTanggal.text = data.transactionDate?.let { convertDate(it) }
                    if (!data.read){
                        Glide.with(binding.root)
                            .load(data.imageUrl)
                            .centerCrop()
                            .into(ivProductImage)
                    }
                    root.setOnClickListener{
                        onItemClick.onClickItem(data)
                    }
                }
            }
        }


    interface OnClickListener{
        fun onClickItem(data: NotificationResponse)
    }
}