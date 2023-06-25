package com.wiliamks.temaqui.ui.personalization.repository

import com.wiliamks.temaqui.commons.Session
import com.wiliamks.temaqui.commons.model.Partner
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PersonalizationRepositoryTest {
    private lateinit var repository: PersonalizationRepository

    @Before
    fun setup() {
        repository = PersonalizationRepositoryImpl()
    }

    @Test
    fun getPartners_success() {
        runBlocking {
            val result = repository.getPartners()

            result.forEachIndexed { index, item ->
                assertEquals(list[index].name, item.name)
                assertEquals(list[index].price, item.price)
            }
        }
    }

    @Test
    fun finishPersonalization_success() {
        runBlocking {
            repository.finishPersonalization(
                arrayListOf(Partner(price = 30.0), Partner(price = 20.0))
            )

            var value = 0.0
            Session.personalizationSelection.forEach {
                value += it.price
            }

            assertEquals(50.0, value)
        }
    }

    companion object {
        private val list = arrayListOf(
            Partner(
                name = "Dona Maria Costura Barras, cortes e ajustes.",
                price = 20.0,
            ),
            Partner(
                name = "Stylos Ellite Customizaçoes em geral.",
                price = 32.0,
            )
        )
    }
}