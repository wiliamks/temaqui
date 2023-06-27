package com.wiliamks.temaqui.ui.buy.viewmodel

import com.wiliamks.temaqui.BaseTest
import com.wiliamks.temaqui.R
import com.wiliamks.temaqui.commons.model.Category
import com.wiliamks.temaqui.commons.model.Product
import com.wiliamks.temaqui.commons.viewmodel.ViewState
import com.wiliamks.temaqui.ui.buy.repository.BuyRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class BuyViewModelTest : BaseTest() {
    private lateinit var viewModel: BuyViewModel

    @MockK
    lateinit var repository: BuyRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        viewModel = BuyViewModel(repository)
    }

    @Test
    fun getCategories_success() {
        coEvery { repository.getCategories() } returns listaCategorias

        viewModel.getCategories()

        assertEquals(viewModel.viewCategories.value, listaCategorias)
        assertEquals(viewModel.viewState.value, ViewState.Success)
    }

    @Test
    fun getCategories_failure() {
        coEvery { repository.getCategories() } throws Exception()

        viewModel.getCategories()

        assertEquals(viewModel.viewState.value, ViewState.Error)
    }

    @Test
    fun getRecomendations_success() {
        coEvery { repository.getRecommendations(any()) } returns listaRecomendacao

        viewModel.getRecommendations(1)

        assertEquals(viewModel.viewRecommendations.value, listaRecomendacao)
        assertEquals(viewModel.viewState.value, ViewState.Success)
    }

    @Test
    fun getRecomendations_failure() {
        coEvery { repository.getRecommendations(any()) } throws Exception()

        viewModel.getRecommendations(1)

        assertEquals(viewModel.viewState.value, ViewState.Error)
    }

    companion object {
        private val listaCategorias = arrayListOf(
            Category(
                name = "Popular",
                picture = R.drawable.card_popular
            ),
            Category(
                name = "Masculino",
                picture = R.drawable.card_masculino
            ),
            Category(
                name = "Feminino",
                picture = R.drawable.card_feminino
            ),
            Category(
                name = "Infantil",
                picture = R.drawable.card_infantil
            ),
        )

        private val listaRecomendacao = arrayListOf(
            Product(
                id = 1,
                name = "Blusa feminina de frio manga longa",
                price = 99.99,
                score = 4.8,
                reviewsCount = 120,
                pictures = arrayListOf(R.drawable.blusa_feminina_de_frio_1, R.drawable.blusa_feminina_de_frio_2, R.drawable.blusa_feminina_de_frio_3),
                sizesAvailable = arrayListOf("P", "M", "G")
            ),
            Product(
                id = 2,
                name = "Boné De Beisebol Liso",
                price = 20.95,
                score = 4.89,
                reviewsCount = 3470,
                pictures = arrayListOf(R.drawable.bone_de_beisebol_masculino_1, R.drawable.bone_de_beisebol_masculino_2, R.drawable.bone_de_beisebol_masculino_3),
                sizesAvailable = arrayListOf("P", "M", "G")
            ),
            Product(
                id = 3,
                name = "Moletom Blusa De Frio Ziper Canguru Capuz",
                price = 199.9,
                score = 4.91,
                reviewsCount = 183,
                pictures = arrayListOf(R.drawable.moleton_blusa_de_frio_canguru_1, R.drawable.moleton_blusa_de_frio_canguru_2, R.drawable.moleton_blusa_de_frio_canguru_3),
                sizesAvailable = arrayListOf("P", "M", "G")
            ),
            Product(
                id = 4,
                name = "Vestido bloco de cores manga de asa de morcego",
                price = 72.99,
                score = 3.1,
                reviewsCount = 85,
                pictures = arrayListOf(R.drawable.vestido_bloco_de_cores_manga_asa_de_morcego_1, R.drawable.vestido_bloco_de_cores_manga_asa_de_morcego_2, R.drawable.vestido_bloco_de_cores_manga_asa_de_morcego_3),
                sizesAvailable = arrayListOf("P", "M", "G")
            ),
        )
    }
}