package com.wiliamks.temaqui.ui.buy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.wiliamks.temaqui.commons.model.Category
import com.wiliamks.temaqui.commons.model.Product
import com.wiliamks.temaqui.commons.viewmodel.BaseViewModel
import com.wiliamks.temaqui.commons.viewmodel.ViewState
import com.wiliamks.temaqui.ui.buy.repository.BuyRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class BuyViewModel(private val repository: BuyRepository) : BaseViewModel() {
    private val categories = MutableLiveData<ArrayList<Category>>()
    private val recommendations = MutableLiveData<ArrayList<Product>>()

    val viewCategories: LiveData<ArrayList<Category>> = categories
    val viewRecommendations: LiveData<ArrayList<Product>> = recommendations

    fun getCategories() {
        state.value = ViewState.Loading
        uiScope.launch {
            try {
                val result = repository.getCategories()

                categories.value = result
                state.value = ViewState.Success
            } catch (_: Exception) {
                state.value = ViewState.Error
            }
        }
    }

    fun getRecommendations(userId: Int) {
        state.value = ViewState.Loading
        uiScope.launch {
            try {
                val result = repository.getRecommendations(userId)

                recommendations.value = result
                state.value = ViewState.Success
            } catch (_: Exception) {
                state.value = ViewState.Error
            }
        }
    }

    companion object {
        @JvmStatic
        fun Factory(buyRepository: BuyRepository) : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                BuyViewModel(repository = buyRepository)
            }
        }
    }
}