package com.wiliamks.temaqui.ui.personalization.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.wiliamks.temaqui.commons.model.Partner
import com.wiliamks.temaqui.commons.viewmodel.BaseViewModel
import com.wiliamks.temaqui.commons.viewmodel.ViewState
import com.wiliamks.temaqui.ui.personalization.repository.PersonalizationRepository
import kotlinx.coroutines.launch

class PersonalizationViewModel(private val repository: PersonalizationRepository) : BaseViewModel() {
    private val personalizationState = MutableLiveData<PersonalizationViewState>()
    val personalizationViewState: LiveData<PersonalizationViewState> = personalizationState

    private val checkedList = arrayListOf<Partner>()

    fun getPartner() {
        uiScope.launch {
            state.value = ViewState.Loading
            try {
                val result = repository.getPartners()

                personalizationState.value = PersonalizationViewState.PartnerList(result)
                updateTotalValue()
                state.value = ViewState.Success
            } catch (error: Exception) {
                state.value = ViewState.Error
            }
        }
    }

    fun checkItem(partner: Partner, checked: Boolean) {
        if (checked) checkedList.add(partner)
        else checkedList.remove(partner)

        updateTotalValue()
    }

    private fun updateTotalValue() {
        var totalValue = 0.0

        checkedList.forEach {
            totalValue += it.price
        }

        personalizationState.value = PersonalizationViewState.TotalValue(totalValue)
    }

    fun finishPersonalization() {
        uiScope.launch {
            repository.finishPersonalization(checkedList)
            personalizationState.value = PersonalizationViewState.FinishedPersonalization
        }
    }

    companion object {
        @JvmStatic
        fun Factory(repository: PersonalizationRepository) = viewModelFactory {
            initializer {
                PersonalizationViewModel(repository)
            }
        }
    }
}