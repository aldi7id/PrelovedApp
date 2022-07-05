package com.preloved.app.ui.fragment.homepage.sell

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.databinding.CategoryItemBinding
import com.preloved.app.ui.listCategory

class BottomSheetCategoryAdapter(
    private val selected: (CategoryResponseItem)->Unit,
    private val unselected: (CategoryResponseItem)->Unit
) :
    RecyclerView.Adapter<BottomSheetCategoryAdapter.ViewHolder>() {
    private val diffCallBack = object : DiffUtil.ItemCallback<CategoryResponseItem>(){

        override fun areItemsTheSame(
            oldItem: CategoryResponseItem,
            newItem: CategoryResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CategoryResponseItem,
            newItem: CategoryResponseItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this,diffCallBack)
    fun submitData(value:List<CategoryResponseItem>?) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(CategoryItemBinding.inflate(inflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: CategoryItemBinding):
        RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(data: CategoryResponseItem){
            binding.apply {
                cbCategory.text = data.name
                cbCategory.isChecked = listCategory.contains(data.name)
                cbCategory.setOnClickListener{
                    if (!listCategory.contains(data.name)){
                        selected.invoke(data)
                    } else {
                        unselected.invoke(data)
                    }
                }
            }
        }
    }

}