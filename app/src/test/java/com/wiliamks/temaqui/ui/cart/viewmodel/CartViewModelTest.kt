package com.wiliamks.temaqui.ui.cart.viewmodel

import com.wiliamks.temaqui.BaseTest
import com.wiliamks.temaqui.commons.model.CartItem
import com.wiliamks.temaqui.commons.model.Partner
import com.wiliamks.temaqui.commons.model.Product
import com.wiliamks.temaqui.ui.cart.repository.CartRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class CartViewModelTest : BaseTest() {
    private lateinit var viewModel: CartViewModel

    @MockK
    lateinit var repository: CartRepository

    private lateinit var arrayStates: ArrayList<CartViewState>

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        viewModel = CartViewModel(repository, list)

        arrayStates = arrayListOf()

        viewModel.cartViewState.observeForever {
            arrayStates.add(it)
        }
    }

    @Test
    fun getDelivery_success() {
        coEvery { repository.getDelivery() } returns 30.0

        viewModel.getDeliveryValue()

        assert(arrayStates.contains(CartViewState.UpdateDelivery(30.0)))
        assert(arrayStates.contains(CartViewState.UpdateCoupon(0.0)))
        assert(arrayStates.contains(CartViewState.UpdateSubTotalValue(130.0)))
        assert(arrayStates.contains(CartViewState.UpdateTotalValue(160.0)))
    }

    @Test
    fun getDelivery_failure() {
        coEvery { repository.getDelivery() } throws Exception("EXCEPTION MOCKADA")

        viewModel.getDeliveryValue()

        assertEquals(CartViewState.Error("EXCEPTION MOCKADA"), viewModel.cartViewState.value)
    }

    @Test
    fun updateCoupon_success() {
        coEvery { repository.getDelivery() } returns 30.0
        coEvery { repository.getCouponValue(any()) } returns 30.0

        viewModel.getDeliveryValue()
        arrayStates.clear()
        viewModel.updateCoupon("30R")

        assert(arrayStates.contains(CartViewState.UpdateDelivery(30.0)))
        assert(arrayStates.contains(CartViewState.UpdateCoupon(30.0)))
        assert(arrayStates.contains(CartViewState.UpdateSubTotalValue(130.0)))
        assert(arrayStates.contains(CartViewState.UpdateTotalValue(130.0)))
    }

    @Test
    fun updateCoupon_failure() {
        coEvery { repository.getDelivery() } returns 30.0
        coEvery { repository.getCouponValue(any()) } returns 0.0

        viewModel.getDeliveryValue()
        arrayStates.clear()
        viewModel.updateCoupon("30R")

        assertEquals(CartViewState.Error("Cupom inválido"), viewModel.cartViewState.value)
    }

    @Test
    fun updateCoupon_error() {
        coEvery { repository.getDelivery() } returns 30.0
        coEvery { repository.getCouponValue(any()) } throws Exception()

        viewModel.getDeliveryValue()
        arrayStates.clear()
        viewModel.updateCoupon("30R")

        assertEquals(CartViewState.Error("Erro ao buscar cupom, tente novamente"), viewModel.cartViewState.value)
    }

    @Test
    fun updateItem_success() {
        coEvery { repository.getDelivery() } returns 30.0

        viewModel.getDeliveryValue()
        arrayStates.clear()

        val product = CartItem(
            product = Product(
                id = 1,
                name = "Blusa feminina de frio manga longa",
                price = 100.00,
                score = 4.8,
                reviewsCount = 120,
                pictures = arrayListOf(),
                sizesAvailable = arrayListOf("P", "M", "G")
            ),
            amountSelected = 2,
            size = "M"
        )
        viewModel.updateItem(product)

        assert(arrayStates.contains(CartViewState.UpdateDelivery(30.0)))
        assert(arrayStates.contains(CartViewState.UpdateCoupon(0.0)))
        assert(arrayStates.contains(CartViewState.UpdateSubTotalValue(230.0)))
        assert(arrayStates.contains(CartViewState.UpdateTotalValue(260.0)))
    }

    companion object {
        val list = arrayListOf(
            CartItem(
                product = Product(
                    id = 1,
                    name = "Blusa feminina de frio manga longa",
                    price = 100.00,
                    score = 4.8,
                    reviewsCount = 120,
                    pictures = arrayListOf(),
                    sizesAvailable = arrayListOf("P", "M", "G")
                ),
                amountSelected = 1,
                size = "M"
            ),
            CartItem(
                partner = Partner(
                    name = "Stylos Ellite Customizaçoes em geral.",
                    price = 30.0,
                    image = 0
                ),
                amountSelected = 1
            )
        )
    }
}