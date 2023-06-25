package com.wiliamks.temaqui.ui.cart.repository

import com.wiliamks.temaqui.commons.Session
import com.wiliamks.temaqui.commons.model.CartItem
import com.wiliamks.temaqui.ui.cart.model.FinishBuyRequest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CartRepositoryTest {
    private lateinit var repository: CartRepository

    @Before
    fun setup() {
        repository = CartRepositoryImpl()
    }

    @Test
    fun finishBuy() {
        runBlocking {
            repository.finishBuy(FinishBuyRequest())

            assertEquals(arrayListOf<CartItem>(), Session.cartSelection)
        }
    }

    @Test
    fun getDelivery() {
        runBlocking {
            assertEquals(15.0, repository.getDelivery())
        }
    }

    @Test
    fun getCoupon10() {
        runBlocking {
            assertEquals(10.0, repository.getCouponValue("10R"))
        }
    }

    @Test
    fun getCoupon20() {
        runBlocking {
            assertEquals(20.0, repository.getCouponValue("20R"))
        }
    }

    @Test
    fun getCoupon30() {
        runBlocking {
            assertEquals(30.0, repository.getCouponValue("30R"))
        }
    }

    @Test
    fun getCoupon40() {
        runBlocking {
            assertEquals(40.0, repository.getCouponValue("40R"))
        }
    }

    @Test
    fun getCoupon50() {
        runBlocking {
            assertEquals(50.0, repository.getCouponValue("50R"))
        }
    }

    @Test
    fun getInvalidCoupon() {
        runBlocking {
            assertEquals(0.0, repository.getCouponValue("0R"))
        }
    }
}