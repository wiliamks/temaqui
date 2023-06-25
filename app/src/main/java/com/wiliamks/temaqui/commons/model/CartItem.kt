package com.wiliamks.temaqui.commons.model

data class CartItem(
    val product: Product? = null,
    val partner: Partner? = null,
    var amountSelected: Int = 0,
    val size: String = ""
)
