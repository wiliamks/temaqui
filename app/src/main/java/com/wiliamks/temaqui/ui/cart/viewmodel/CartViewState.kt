package com.wiliamks.temaqui.ui.cart.viewmodel

import java.util.zip.DeflaterOutputStream

sealed class CartViewState {
    data class UpdateCoupon(val value: Double) : CartViewState()
    data class UpdateSubTotalValue(val subTotal: Double) : CartViewState()
    data class UpdateDelivery(val delivery: Double) : CartViewState()
    data class UpdateTotalValue(val total: Double) : CartViewState()
    data class Error(val message: String): CartViewState()
}
