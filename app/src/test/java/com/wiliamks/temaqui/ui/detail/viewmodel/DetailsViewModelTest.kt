package com.wiliamks.temaqui.ui.detail.viewmodel

import com.wiliamks.temaqui.BaseTest
import com.wiliamks.temaqui.R
import com.wiliamks.temaqui.commons.model.Product
import com.wiliamks.temaqui.commons.viewmodel.ViewState
import com.wiliamks.temaqui.ui.detail.repository.DetailsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class DetailsViewModelTest : BaseTest() {
    private lateinit var viewModel: DetailsViewModel

    @MockK
    lateinit var repository: DetailsRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        viewModel = DetailsViewModel(product, repository)
    }

    @Test
    fun addItem_success() {
        coEvery { repository.addItemToCart(any()) } returns Unit

        viewModel.addItemToCart(1, "M")

        assertEquals(ViewState.Success, viewModel.viewState.value)
    }

    @Test
    fun addItem_error() {
        coEvery { repository.addItemToCart(any()) } throws Exception()

        viewModel.addItemToCart(1, "M")

        assertEquals(ViewState.Error, viewModel.viewState.value)
    }

    companion object {
        private val product = Product(
            id = 1,
            name = "Blusa feminina de frio manga longa",
            price = 100.00,
            score = 4.8,
            reviewsCount = 120,
            pictures = arrayListOf(R.drawable.blusa_feminina_de_frio_1, R.drawable.blusa_feminina_de_frio_2, R.drawable.blusa_feminina_de_frio_3),
            sizesAvailable = arrayListOf("P", "M", "G")
        )
    }
}