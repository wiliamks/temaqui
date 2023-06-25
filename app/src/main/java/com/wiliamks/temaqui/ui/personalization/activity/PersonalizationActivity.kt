package com.wiliamks.temaqui.ui.personalization.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.wiliamks.temaqui.commons.model.Partner
import com.wiliamks.temaqui.commons.toBRL
import com.wiliamks.temaqui.databinding.ActivityPersonalizeBinding
import com.wiliamks.temaqui.ui.personalization.adapter.PersonalizationAdapter
import com.wiliamks.temaqui.ui.personalization.repository.PersonalizationRepositoryImpl
import com.wiliamks.temaqui.ui.personalization.viewmodel.PersonalizationViewModel
import com.wiliamks.temaqui.ui.personalization.viewmodel.PersonalizationViewState
import java.util.ArrayList

class PersonalizationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalizeBinding

    private val viewModel by viewModels<PersonalizationViewModel> {
        val repository = PersonalizationRepositoryImpl()
        PersonalizationViewModel.Factory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPersonalizeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.personalizationViewState.observe(this) {
            when (it) {
                is PersonalizationViewState.PartnerList -> {
                    configureRecycler(it.list)
                }

                is PersonalizationViewState.TotalValue -> {
                    binding.textTotalContent.text = it.value.toBRL()
                }

                PersonalizationViewState.FinishedPersonalization -> {
                    onBackPressed()
                }
            }
        }

        viewModel.getPartner()
    }

    private fun configureRecycler(list: ArrayList<Partner>) {
        binding.recyclerCart.adapter = PersonalizationAdapter(list) { item, checked ->
            viewModel.checkItem(item, checked)
        }

        binding.recyclerCart.addItemDecoration(PersonalizationAdapter.VerticalMarginItemDecoration)

        binding.buttonFinish.setOnClickListener {
            viewModel.finishPersonalization()
        }
    }
}