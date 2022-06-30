package com.preloved.app.ui.product.add

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.preloved.app.data.network.model.response.CategoryResponseItem
import com.preloved.app.databinding.CategoryItemBinding

class CategoryAdapter(
    private val mContext: Context,
    private val mLayoutResourceId: Int,
    categories: List<CategoryResponseItem>
) : ArrayAdapter<CategoryResponseItem>(mContext, mLayoutResourceId, categories){
    private val category: MutableList<CategoryResponseItem> = ArrayList(categories)
    private val allCategory: List<CategoryResponseItem> = ArrayList(categories)

    override fun getCount(): Int {
        return category.size
    }

    override fun getItem(position: Int): CategoryResponseItem? {
        return category[position]
    }

    override fun getItemId(position: Int): Long {
        return category[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater = (mContext as Activity).layoutInflater
            convertView = inflater.inflate(mLayoutResourceId, parent, false)
        }
        return convertView!!
    }
}