package com.wiliamks.temaqui.commons

import com.wiliamks.temaqui.commons.model.CartItem
import com.wiliamks.temaqui.commons.model.Partner
import com.wiliamks.temaqui.commons.model.Product

internal object Session {
    var personalizationSelection = arrayListOf<Partner>()
    var currentSelectedProduct: Product? = null
    var cartSelection = arrayListOf<CartItem>()

    fun getSubTotal() : Double {
        var amount = 0.0
        cartSelection.forEach {
            if (it.product != null) amount += it.product.price
            else if (it.partner != null) amount += it.partner.price
        }

        return amount
    }
}