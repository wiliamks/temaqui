package com.wiliamks.temaqui.ui.detail.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.wiliamks.temaqui.commons.model.CartItem
import com.wiliamks.temaqui.commons.model.Product
import com.wiliamks.temaqui.commons.viewmodel.BaseViewModel
import com.wiliamks.temaqui.commons.viewmodel.ViewState
import com.wiliamks.temaqui.ui.detail.repository.DetailsRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailsViewModel(
    private val product: Product,
    private val repository: DetailsRepository
) : BaseViewModel() {

    fun addItemToCart(amount: Int, size: String) {
        state.value = ViewState.Loading
        uiScope.launch {
            try {
                val body = CartItem(
                    product = product,
                    amountSelected = amount,
                    size = size
                )
                val result = repository.addItemToCart(body)
                state.value = ViewState.Success
            } catch (error: Exception) {
                state.value = ViewState.Error
            }
        }
    }

    companion object {
        @JvmStatic
        fun Factory(product: Product, detailsRepository: DetailsRepository) = viewModelFactory {
            initializer {
                DetailsViewModel(product = product, repository = detailsRepository)
            }
        }
    }
}