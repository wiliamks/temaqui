package com.wiliamks.temaqui.ui.buy.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.wiliamks.temaqui.commons.Session
import com.wiliamks.temaqui.commons.model.Category
import com.wiliamks.temaqui.commons.model.Product
import com.wiliamks.temaqui.databinding.FragmentBuyBinding
import com.wiliamks.temaqui.ui.buy.adapter.BuyCategoryAdapter
import com.wiliamks.temaqui.ui.buy.adapter.BuyRecomendationAdapter
import com.wiliamks.temaqui.ui.buy.repository.BuyRepositoryImpl
import com.wiliamks.temaqui.ui.buy.viewmodel.BuyViewModel
import com.wiliamks.temaqui.ui.detail.activity.DetailActivity


class BuyFragment : Fragment() {

    private var _binding: FragmentBuyBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<BuyViewModel> {
        val repository = BuyRepositoryImpl()
        BuyViewModel.Factory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuyBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureViewModel()
    }

    private fun configureViewModel() {
        viewModel.viewCategories.observe(viewLifecycleOwner) {
            configureCategoryAdapter(it)
        }

        viewModel.viewRecommendations.observe(viewLifecycleOwner) {
            configureRecommendationAdapter(it)
        }

        viewModel.getCategories()
        viewModel.getRecommendations(1)
    }

    private fun configureCategoryAdapter(listCategory: ArrayList<Category>) {
        binding.recyclerCategory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        if (binding.recyclerCategory.itemDecorationCount == 0)
            binding.recyclerCategory.addItemDecoration(BuyCategoryAdapter.HorizontalMarginItemDecoration)
        binding.recyclerCategory.adapter = BuyCategoryAdapter(listCategory) {
            Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
        }
    }

    private fun configureRecommendationAdapter(listRecommendation: ArrayList<Product>) {
        binding.recyclerRecomendations.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        if (binding.recyclerRecomendations.itemDecorationCount == 0)
            binding.recyclerRecomendations.addItemDecoration(BuyRecomendationAdapter.VerticalMarginItemDecoration)
        binding.recyclerRecomendations.adapter = BuyRecomendationAdapter(listRecommendation) {
            Session.currentSelectedProduct = it
            val intent = Intent(context, DetailActivity::class.java)
            activity?.startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}