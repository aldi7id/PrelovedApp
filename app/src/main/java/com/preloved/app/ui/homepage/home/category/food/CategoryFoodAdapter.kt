package com.preloved.app.ui.homepage.home.category.food

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.databinding.CardItemCategoryBinding

class CategoryFoodAdapter (private val onClick: (LoginRequest) -> Unit) :
    ListAdapter<LoginRequest, CategoryFoodAdapter.CategoryHolder>(Differ()) {
    class CategoryHolder(
        private val binding: CardItemCategoryBinding,
        private val onClick: (LoginRequest) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loginRequest: LoginRequest) {
            binding.apply {
                with(loginRequest) {
                    Glide.with(root)
                        .load(email)
                        .circleCrop()
                        .into(sivImageItem)
                    tvNameItem.text = email
                    tvCategoryItem.text = email
                    tvPriceItem.text = email
                    root.setOnClickListener {
                        onClick(loginRequest)
                    }
                }
            }
        }

    }

    class Differ : DiffUtil.ItemCallback<LoginRequest>() {
        override fun areItemsTheSame(oldItem: LoginRequest, newItem: LoginRequest): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LoginRequest, newItem: LoginRequest): Boolean {
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