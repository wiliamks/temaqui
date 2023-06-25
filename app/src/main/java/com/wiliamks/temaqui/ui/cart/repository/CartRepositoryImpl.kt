package com.wiliamks.temaqui.ui.cart.repository

import com.wiliamks.temaqui.commons.Session
import com.wiliamks.temaqui.ui.cart.model.FinishBuyRequest

class CartRepositoryImpl : CartRepository {
    override suspend fun finishBuy(body: FinishBuyRequest) {
        Session.cartSelection.clear()
    }
    override suspend fun getDelivery() = 15.0
    override suspend fun getCouponValue(coupon: String) =
        when (coupon) {
            TEN_COUPON -> 10.0
            TWENTY_COUPON -> 20.0
            THIRTY_COUPON -> 30.0
            FORTY_COUPON -> 40.0
            FIFTY_COUPON -> 50.0
            else -> 0.0
        }

    companion object {
        private const val TEN_COUPON = "10R"
        private const val TWENTY_COUPON = "20R"
        private const val THIRTY_COUPON = "30R"
        private const val FORTY_COUPON = "40R"
        private const val FIFTY_COUPON = "50R"
    }
}