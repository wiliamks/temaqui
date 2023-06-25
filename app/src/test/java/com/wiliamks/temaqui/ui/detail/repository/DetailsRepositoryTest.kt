package com.wiliamks.temaqui.ui.detail.repository

import com.wiliamks.temaqui.R
import com.wiliamks.temaqui.commons.Session
import com.wiliamks.temaqui.commons.model.CartItem
import com.wiliamks.temaqui.commons.model.Product
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DetailsRepositoryTest {
    private lateinit var repository: DetailsRepository

    @Before
    fun setup() {
        repository = DetailsRepositoryImpl()
    }

    @Test
    fun addItem_success() {
        val item = CartItem(
            product = Product(
                id = 1,
                name = "Blusa feminina de frio manga longa",
                price = 100.00,
                score = 4.8,
                reviewsCount = 120,
                pictures = arrayListOf(R.drawable.blusa_feminina_de_frio_1, R.drawable.blusa_feminina_de_frio_2, R.drawable.blusa_feminina_de_frio_3),
                sizesAvailable = arrayListOf("P", "M", "G")
            ),
            amountSelected = 1,
            size = "M"
        )

        runBlocking {
            repository.addItemToCart(item)

            assert(Session.cartSelection.contains(item))
        }
    }
}