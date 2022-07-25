package com.preloved.app.ui.fragment.homepage.account.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.preloved.app.data.network.model.response.whislist.GetWishlistResponse
import com.preloved.app.databinding.CardItemCategoryBinding
import com.preloved.app.ui.currency

class WishlistAdapter(private val onClick: (GetWishlistResponse.GetWishlistResponseItem) -> Unit) :
    ListAdapter<GetWishlistResponse.GetWishlistResponseItem, WishlistAdapter.CategoryHolder>(Differ()) {
    class CategoryHolder(
        private val binding: CardItemCategoryBinding,
        private val onClick: (GetWishlistResponse.GetWishlistResponseItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(CategoryResponseItem: GetWishlistResponse.GetWishlistResponseItem) {
            binding.apply {
                with(CategoryResponseItem) {
                    Glide.with(root)
                        .load(product.imageUrl)
                        .centerCrop()
                        .into(sivImageItem)
                    tvNameItem.text = product.name
                    tvCategoryItem.text = product.categories.joinToString{
                        it.name
                    }
                    tvPriceItem.text = currency(product.basePrice)
                    root.setOnClickListener {
                        onClick(CategoryResponseItem)
                    }
                }
            }
        }
    }

    class Differ : DiffUtil.ItemCallback<GetWishlistResponse.GetWishlistResponseItem>() {
        override fun areItemsTheSame(oldItem: GetWishlistResponse.GetWishlistResponseItem, newItem: GetWishlistResponse.GetWishlistResponseItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GetWishlistResponse.GetWishlistResponseItem, newItem: GetWishlistResponse.GetWishlistResponseItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val binding =
            CardItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        return holder.bind(getItem(position))
    }
}