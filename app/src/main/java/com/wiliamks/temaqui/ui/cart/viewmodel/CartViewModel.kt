package com.wiliamks.temaqui.ui.cart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.wiliamks.temaqui.commons.model.CartItem
import com.wiliamks.temaqui.commons.viewmodel.BaseViewModel
import com.wiliamks.temaqui.ui.cart.repository.CartRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class CartViewModel(
    private val repository: CartRepository,
    private val items: ArrayList<CartItem>
) : BaseViewModel() {
    private val cartState = MutableLiveData<CartViewState>()
    private var coupon = 0.0
    private var delivery = 0.0

    val cartViewState: LiveData<CartViewState> = cartState

    fun getDeliveryValue() {
        uiScope.launch {
            try {
                delivery = repository.getDelivery()
                updateValues()
            } catch (error: Exception) {
                cartState.value = CartViewState.Error(error.message ?: "")
            }
        }
    }

    fun updateItem(cartItem: CartItem) {
        val item = items.find { it.product?.id == cartItem.product?.id }
        item?.let {
            it.amountSelected = cartItem.amountSelected
            updateValues()
        }
    }

    fun updateCoupon(couponText: String) {
        uiScope.launch {
            try {
                coupon = repository.getCouponValue(couponText)
                if (coupon == 0.0) cartState.value = CartViewState.Error("Cupom inválido")
                else updateValues()
            } catch (error: Exception) {
                cartState.value = CartViewState.Error("Erro ao buscar cupom, tente novamente")
            }
        }
    }

    private fun updateValues() {
        var subTotal = 0.0
        items.forEach {
            if (it.product != null) subTotal += it.product.price * it.amountSelected
            else if (it.partner != null) subTotal += it.partner.price
        }

        cartState.value = CartViewState.UpdateSubTotalValue(subTotal)

        cartState.value = CartViewState.UpdateDelivery(delivery)

        cartState.value = CartViewState.UpdateCoupon(coupon)

        val total = subTotal + delivery - coupon
        cartState.value = CartViewState.UpdateTotalValue(total)
    }

    companion object {
        @JvmStatic
        fun Factory(repository: CartRepository, items: ArrayList<CartItem>) = viewModelFactory {
            initializer {
                CartViewModel(repository, items)
            }
        }
    }
}