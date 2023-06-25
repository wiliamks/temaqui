package com.wiliamks.temaqui.ui.personalization.viewmodel

import com.wiliamks.temaqui.commons.model.Partner

sealed class PersonalizationViewState {
    data class PartnerList(val list: ArrayList<Partner>) : PersonalizationViewState()
    data class TotalValue(val value: Double) : PersonalizationViewState()
    object FinishedPersonalization : PersonalizationViewState()
}
