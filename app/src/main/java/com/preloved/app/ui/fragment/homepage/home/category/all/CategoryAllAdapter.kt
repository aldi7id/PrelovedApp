package com.preloved.app.ui.fragment.homepage.home.category.all

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.databinding.CardItemCategoryBinding

class CategoryAllAdapter(private val onClick: (CategoryResponse.CategoryResponseItem) -> Unit) :
    ListAdapter<CategoryResponse.CategoryResponseItem, CategoryAllAdapter.CategoryHolder>(Differ()) {
    class CategoryHolder(
        private val binding: CardItemCategoryBinding,
        private val onClick: (CategoryResponse.CategoryResponseItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(CategoryResponseItem: CategoryResponse.CategoryResponseItem) {
            binding.apply {
                with(CategoryResponseItem) {
                    Glide.with(root)
                        .load(imageUrl)
                        .circleCrop()
                        .into(sivImageItem)
                    tvNameItem.text = name
                    val listCategory = arrayListOf<String>()
                    categories.forEach{
                        listCategory.add(it.name)
                    }
                    tvCategoryItem.text = listCategory.toString()
                    tvPriceItem.text = basePrice.toString()
                    root.setOnClickListener {
                        onClick(CategoryResponseItem)
                    }
                }
            }
        }

    }

    class Differ : DiffUtil.ItemCallback<CategoryResponse.CategoryResponseItem>() {
        override fun areItemsTheSame(oldItem: CategoryResponse.CategoryResponseItem, newItem: CategoryResponse.CategoryResponseItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CategoryResponse.CategoryResponseItem, newItem: CategoryResponse.CategoryResponseItem): Boolean {
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