package com.wiliamks.temaqui.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.smarteist.autoimageslider.SliderViewAdapter
import com.wiliamks.temaqui.databinding.ItemSliderBinding

class SliderAdapter(private val list: ArrayList<Int>) : SliderViewAdapter<SliderAdapter.SliderViewHolder>() {

    override fun getCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        val context = parent.context
        val binding = ItemSliderBinding.inflate(LayoutInflater.from(context), parent, false)

        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        val image = list[position]
        viewHolder.bind(image)
    }

    inner class SliderViewHolder(private val bindind: ItemSliderBinding) : ViewHolder(bindind.root) {
        fun bind(image: Int) {
            bindind.sliderProduct.setImageResource(image)
        }
    }
}