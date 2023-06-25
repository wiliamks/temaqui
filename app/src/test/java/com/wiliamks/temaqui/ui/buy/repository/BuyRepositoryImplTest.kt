package com.wiliamks.temaqui.ui.buy.repository

import com.wiliamks.temaqui.R
import com.wiliamks.temaqui.commons.model.Category
import com.wiliamks.temaqui.commons.model.Product
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class BuyRepositoryImplTest {
    private lateinit var repository: BuyRepository

    @Before
    fun setup() {
        repository = BuyRepositoryImpl()
    }

    @Test
    fun verify_categories() {
        assertEquals(repository.getCategories(), listaCategorias)
    }

    @Test
    fun verify_recomendations() {
        assertEquals(repository.getRecommendations(1), listaRecomendacao)
    }

    companion object {
        private val listaCategorias = arrayListOf(
            Category(
                name = "Popular",
                picture = R.drawable.popular
            ),
            Category(
                name = "Masculino",
                picture = R.drawable.masculino
            ),
            Category(
                name = "Feminino",
                picture = R.drawable.feminino
            ),
            Category(
                name = "Infantil",
                picture = R.drawable.infantil
            ),
        )

        private val listaRecomendacao = arrayListOf(
            Product(
                id = 1,
                name = "Blusa feminina de frio manga longa",
                price = 99.99,
                score = 4.8,
                soldBy = "Shein",
                originalLink = "https://br.shein.com/",
                reviewsCount = 120,
                pictures = arrayListOf(R.drawable.blusa_feminina_de_frio_1, R.drawable.blusa_feminina_de_frio_2, R.drawable.blusa_feminina_de_frio_3),
                sizesAvailable = arrayListOf("P", "M", "G")
            ),
            Product(
                id = 2,
                name = "Boné De Beisebol Liso",
                price = 20.95,
                score = 4.89,
                soldBy = "Renner",
                originalLink = "https://www.lojasrenner.com.br/",
                reviewsCount = 3470,
                pictures = arrayListOf(R.drawable.bone_de_beisebol_masculino_1, R.drawable.bone_de_beisebol_masculino_2, R.drawable.bone_de_beisebol_masculino_3),
                sizesAvailable = arrayListOf("P", "M", "G")
            ),
            Product(
                id = 3,
                name = "Moletom Blusa De Frio Ziper Canguru Capuz",
                price = 199.9,
                score = 4.91,
                soldBy = "Riachuelo",
                originalLink = "https://www.riachuelo.com.br/",
                reviewsCount = 183,
                pictures = arrayListOf(R.drawable.moleton_blusa_de_frio_canguru_1, R.drawable.moleton_blusa_de_frio_canguru_2, R.drawable.moleton_blusa_de_frio_canguru_3),
                sizesAvailable = arrayListOf("P", "M", "G")
            ),
            Product(
                id = 4,
                name = "Vestido bloco de cores manga de asa de morcego",
                price = 72.99,
                score = 3.1,
                soldBy = "C&A",
                originalLink = "https://www.cea.com.br/",
                reviewsCount = 85,
                pictures = arrayListOf(R.drawable.vestido_bloco_de_cores_manga_asa_de_morcego_1, R.drawable.vestido_bloco_de_cores_manga_asa_de_morcego_2, R.drawable.vestido_bloco_de_cores_manga_asa_de_morcego_3),
                sizesAvailable = arrayListOf("P", "M", "G")
            ),
        )
    }
}