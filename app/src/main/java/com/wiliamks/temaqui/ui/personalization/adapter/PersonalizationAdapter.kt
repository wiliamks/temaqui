package com.wiliamks.temaqui.ui.personalization.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wiliamks.temaqui.commons.model.Partner
import com.wiliamks.temaqui.commons.toBRL
import com.wiliamks.temaqui.databinding.ItemPartnerBinding

class PersonalizationAdapter(
    private val list: ArrayList<Partner>,
    private val callback: (Partner, Boolean) -> Unit
) : RecyclerView.Adapter<PersonalizationAdapter.PersonalizationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalizationViewHolder {
        val context = parent.context
        val binding = ItemPartnerBinding.inflate(LayoutInflater.from(context), parent, false)

        return PersonalizationViewHolder(binding, callback)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PersonalizationViewHolder, position: Int) {
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

    inner class PersonalizationViewHolder(
        private val binding: ItemPartnerBinding,
        private val callback: (Partner, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Partner) {
            binding.imgContent.setImageResource(item.image)
            binding.textName.text = item.name
            binding.textPrice.text = item.price.toBRL()
            binding.checkboxItem.setOnCheckedChangeListener { _, value ->
                callback(item, value)
            }
        }
    }
}