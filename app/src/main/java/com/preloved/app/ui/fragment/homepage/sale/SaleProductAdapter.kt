package com.preloved.app.ui.fragment.homepage.sale

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.preloved.app.data.network.model.response.SellerProductResponseItem
import com.preloved.app.databinding.ItemProductHomeBinding
import com.preloved.app.ui.currency
import com.preloved.app.ui.listCategoryId


class SaleProductAdapter(private val onItemClick: OnclickListener):
RecyclerView.Adapter<SaleProductAdapter.ViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<SellerProductResponseItem>(){
        override fun areItemsTheSame(
            oldItem: SellerProductResponseItem,
            newItem: SellerProductResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SellerProductResponseItem,
            newItem: SellerProductResponseItem
        ): Boolean {
            return oldItem.name == newItem.name
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitData(value: List<SellerProductResponseItem>?) = differ.submitList(value)

    inner class ViewHolder(private val  binding: ItemProductHomeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: SellerProductResponseItem) {
            binding.apply {
                Glide.with(binding.root)
                    .load(data.imageUrl)
                    .into(ivProduk)
                tvNamaProduk.text = data.name
                if(data.categories.isNotEmpty()){
                    if(data.categories.size > 2 ){
                        tvKategori.text =
                            "${data.categories[0].name}, ${data.categories[1].name}, ${data.categories[2].name}"
                    } else if (data.categories.size > 1 ){
                        tvKategori.text = "${data.categories[0].name}, ${data.categories[1].name}"
                    } else {
                        tvKategori.text = "${data.categories[0].name}"
                    }
                }
                val price = currency(data.basePrice)
                tvHarga.text = price
                root.setOnClickListener {
                    onItemClick.onClickItem(data)
                }
            }
        }
    }
    interface OnclickListener {
        fun onClickItem(data: SellerProductResponseItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        if (viewType == 1) {
            // inflate your first item layout & return that viewHolder

        } else {
            //return ViewHolder(ItemProductHomeBinding.inflate(inflater, parent, false))
        }
        return ViewHolder(ItemProductHomeBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let{
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}