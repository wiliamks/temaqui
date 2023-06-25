package com.wiliamks.temaqui.ui.buy.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wiliamks.temaqui.commons.model.Product
import com.wiliamks.temaqui.commons.toBRL
import com.wiliamks.temaqui.databinding.ItemRecomendacaoBinding

class BuyRecomendationAdapter(
    private val list: ArrayList<Product>,
    private val callback: (Product) -> Unit
) : RecyclerView.Adapter<BuyRecomendationAdapter.RecomendationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecomendationViewHolder {
        val context = parent.context
        val binding = ItemRecomendacaoBinding.inflate(LayoutInflater.from(context), parent, false)

        return RecomendationViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecomendationViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, callback)
    }

    inner class RecomendationViewHolder(
        private val binding: ItemRecomendacaoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product, callback: (Product) -> Unit) {
            binding.imgProduct.setImageResource(item.pictures[0])
            binding.textTitle.text = item.name
            binding.textScore.text = item.score.toString()
            binding.textPrice.text = item.price.toBRL()

            binding.root.setOnClickListener {
                callback(item)
            }
        }
    }

    object VerticalMarginItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            with(outRect) {
                val spaceSize = 16
                if (parent.getChildAdapterPosition(view) % 2 != 0) {
                    left = spaceSize
                    //top = spaceSize
                } else {
                    right = spaceSize
                }

                if (!listOf(0, 1).contains(parent.getChildAdapterPosition(view))) {
                    top = spaceSize
                }
                bottom = spaceSize
            }
        }
    }
}