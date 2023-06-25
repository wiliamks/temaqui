package com.wiliamks.temaqui.ui.cart.model

import com.wiliamks.temaqui.commons.model.CartItem

data class FinishBuyRequest(
    val items: ArrayList<CartItem> = arrayListOf(),
    val coupon: String? = null
)
