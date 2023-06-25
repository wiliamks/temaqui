package com.wiliamks.temaqui.ui.personalization.repository

import com.wiliamks.temaqui.commons.model.Partner

interface PersonalizationRepository {
    suspend fun getPartners() : ArrayList<Partner>
    suspend fun finishPersonalization(checkedList: ArrayList<Partner>)
}