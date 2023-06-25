package com.wiliamks.temaqui.ui.buy.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wiliamks.temaqui.commons.model.Category
import com.wiliamks.temaqui.databinding.ItemCategoriaBinding

class BuyCategoryAdapter(
    private val list: ArrayList<Category>,
    private val callback: (Category) -> Unit
) : RecyclerView.Adapter<BuyCategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val context = parent.context
        val binding = ItemCategoriaBinding.inflate(LayoutInflater.from(context), parent, false)

        return CategoryViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, callback)
    }

    inner class CategoryViewHolder(
        private val binding: ItemCategoriaBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category, callback: (Category) -> Unit) {
            binding.imgCircle.setImageResource(item.picture)
            binding.textTitle.text = item.name
            binding.root.setOnClickListener {
                callback(item)
            }
        }
    }

    object HorizontalMarginItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            with(outRect) {
                val spaceSize = 22
                if (parent.getChildAdapterPosition(view) != 0) {
                    left = spaceSize
                    //top = spaceSize
                }
                right = spaceSize
                bottom = spaceSize
            }
        }
    }
}