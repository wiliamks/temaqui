package com.wiliamks.temaqui.ui.personalization.repository

import com.wiliamks.temaqui.R
import com.wiliamks.temaqui.commons.Session
import com.wiliamks.temaqui.commons.model.Partner

class PersonalizationRepositoryImpl : PersonalizationRepository {
    override suspend fun getPartners() = list
    override suspend fun finishPersonalization(checkedList: ArrayList<Partner>) {
        Session.personalizationSelection = checkedList
    }

    companion object {
        private val list = arrayListOf(
            Partner(
                name = "Dona Maria Costura Barras, cortes e ajustes.",
                price = 20.0,
                image = R.drawable.dona_maria
            ),
            Partner(
                name = "Stylos Ellite Customizaçoes em geral.",
                price = 32.0,
                image = R.drawable.styllos
            )
        )
    }
}