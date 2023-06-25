package com.wiliamks.temaqui.ui.cart.repository

import com.wiliamks.temaqui.ui.cart.model.FinishBuyRequest

interface CartRepository {
    suspend fun finishBuy(body: FinishBuyRequest)
    suspend fun getDelivery() : Double


    suspend fun getCouponValue(coupon: String) : Double
}