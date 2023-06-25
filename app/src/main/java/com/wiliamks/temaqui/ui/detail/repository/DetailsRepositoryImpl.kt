package com.wiliamks.temaqui.ui.detail.repository

import com.wiliamks.temaqui.commons.Session
import com.wiliamks.temaqui.commons.model.CartItem

class DetailsRepositoryImpl : DetailsRepository {
    override suspend fun addItemToCart(item: CartItem) {
        Session.cartSelection.forEachIndexed { index, cartItem ->
            if (cartItem.product?.id == item.product?.id) {
                Session.cartSelection.removeAt(index)
            }
        }

        Session.cartSelection.add(item)

        Session.personalizationSelection.forEach {
            Session.cartSelection.add(
                CartItem(
                    partner = it,
                    amountSelected = 1
                )
            )
        }
    }
}