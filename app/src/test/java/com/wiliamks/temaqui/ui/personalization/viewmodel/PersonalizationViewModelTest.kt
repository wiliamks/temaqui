package com.wiliamks.temaqui.ui.personalization.viewmodel

import com.wiliamks.temaqui.BaseTest
import com.wiliamks.temaqui.commons.Session
import com.wiliamks.temaqui.commons.model.Partner
import com.wiliamks.temaqui.commons.viewmodel.ViewState
import com.wiliamks.temaqui.ui.personalization.repository.PersonalizationRepository
import com.wiliamks.temaqui.ui.personalization.repository.PersonalizationRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class PersonalizationViewModelTest : BaseTest() {
    private lateinit var viewModel: PersonalizationViewModel
    private lateinit var arrayStates: ArrayList<PersonalizationViewState>
    private lateinit var arrayViewStates: ArrayList<ViewState>

    @MockK
    lateinit var repository: PersonalizationRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        viewModel = PersonalizationViewModel(repository)

        arrayStates = arrayListOf()
        arrayViewStates = arrayListOf()

        viewModel.personalizationViewState.observeForever {
            arrayStates.add(it)
        }

        viewModel.viewState.observeForever {
            arrayViewStates.add(it)
        }
    }

    @Test
    fun getPartner_success() {
        coEvery { repository.getPartners() } returns list

        viewModel.getPartner()

        assert(arrayStates.contains(PersonalizationViewState.PartnerList(list)))
        assert(arrayStates.contains(PersonalizationViewState.TotalValue(0.0)))
        assert(arrayViewStates.contains(ViewState.Loading))
        assert(arrayViewStates.contains(ViewState.Success))
    }

    @Test
    fun getPartner_error() {
        coEvery { repository.getPartners() } throws Exception()

        viewModel.getPartner()

        assert(arrayViewStates.contains(ViewState.Loading))
        assert(arrayViewStates.contains(ViewState.Error))
    }

    @Test
    fun checkItem_valueTwenty() {
        viewModel.checkItem(list[0], true)

        assert(arrayStates.contains(PersonalizationViewState.TotalValue(20.0)))
    }

    @Test
    fun checkItem_valueFiftyTwo() {
        viewModel.checkItem(list[0], true)
        viewModel.checkItem(list[1], true)

        assert(arrayStates.contains(PersonalizationViewState.TotalValue(52.0)))
    }

    @Test
    fun finishPersonalization_success() {
        repository = PersonalizationRepositoryImpl()
        viewModel = PersonalizationViewModel(repository)

        viewModel.checkItem(list[0], true)
        viewModel.checkItem(list[1], true)
        viewModel.finishPersonalization()

        assertEquals(list, Session.personalizationSelection)
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