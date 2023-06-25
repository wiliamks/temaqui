package com.wiliamks.temaqui.ui.cart.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wiliamks.temaqui.commons.model.CartItem
import com.wiliamks.temaqui.commons.toBRL
import com.wiliamks.temaqui.databinding.ItemCartBinding

class CartAdapter(
    private val list: ArrayList<CartItem>,
    private val callback: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val context = parent.context
        val binding = ItemCartBinding.inflate(LayoutInflater.from(context), parent, false)

        return CartViewHolder(binding, callback)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    object VerticalMarginItemDecoration : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            with(outRect) {
                val spaceSize = 16
                if (parent.getChildAdapterPosition(view) != 0) {
                    top = spaceSize
                }
            }
        }
    }

    inner class CartViewHolder(
        private val binding: ItemCartBinding,
        callback: (CartItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            if (item.product != null ){
                binding.imgContent.setImageResource(item.product.pictures[1])
                binding.textName.text = item.product.name
                binding.textPrice.text = (item.product.price * item.amountSelected).toBRL()
                binding.textSizeContent.text = item.size
            } else if (item.partner != null) {
                binding.imgContent.setImageResource(item.partner.image)
                binding.textName.text = item.partner.name
                binding.textPrice.text = (item.partner.price * item.amountSelected).toBRL()
                binding.textSizeTitle.visibility = View.INVISIBLE
                binding.textSizeContent.visibility = View.INVISIBLE
            }
            binding.textQuantity.text = item.amountSelected.toString()

            binding.buttonPlus.setOnClickListener {
                item.amountSelected += 1
                binding.textQuantity.text = item.amountSelected.toString()
                callback(item)
            }

            binding.buttonMinus.setOnClickListener {
                if (item.amountSelected > 1) {
                    item.amountSelected -= 1
                    binding.textQuantity.text = item.amountSelected.toString()
                    callback(item)
                }
            }
        }
    }
}