package com.wiliamks.temaqui.ui.detail.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wiliamks.temaqui.R
import com.wiliamks.temaqui.databinding.ItemListImagesBinding

class PicturesListAdapter(
    private val list: ArrayList<Int>,
    private var currentSelectedIndex: Int,
    private val callback: (Int) -> Unit
) : RecyclerView.Adapter<PicturesListAdapter.PicturesListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesListViewHolder {
        val context = parent.context
        val binding = ItemListImagesBinding.inflate(LayoutInflater.from(context), parent, false)
        return PicturesListViewHolder(binding, callback)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PicturesListViewHolder, position: Int) {
        val image = list[position]
        holder.bind(image, currentSelectedIndex == position, position)
    }

    fun changePosition(newPosition: Int) {
        val previousSelected = currentSelectedIndex
        if (newPosition >= 0 && newPosition < list.size) {
            currentSelectedIndex = newPosition
            notifyItemChanged(newPosition)
            notifyItemChanged(previousSelected)
        }
    }

    inner class PicturesListViewHolder(
        private val binding: ItemListImagesBinding,
        private val callback: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Int, isSelected: Boolean, position: Int) {
            binding.imgProduct.setImageResource(image)
            if (isSelected)
                binding.cardPicture.strokeColor = binding.root.context.getColor(R.color.category_border)
            else
                binding.cardPicture.strokeColor = binding.root.context.getColor(R.color.white)
            binding.root.setOnClickListener {
                callback(position)
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
                val spaceSize = 16
                if (parent.getChildAdapterPosition(view) != 0) {
                    left = spaceSize
                }
                right = spaceSize
                bottom = spaceSize
            }
        }
    }
}