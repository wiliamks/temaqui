package com.wiliamks.temaqui.ui.detail.repository

import com.wiliamks.temaqui.commons.model.CartItem

interface DetailsRepository {
    suspend fun addItemToCart(item: CartItem)
}