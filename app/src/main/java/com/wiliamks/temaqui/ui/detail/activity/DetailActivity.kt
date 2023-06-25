package com.wiliamks.temaqui.ui.detail.activity

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wiliamks.temaqui.R
import com.wiliamks.temaqui.commons.Session
import com.wiliamks.temaqui.commons.model.Product
import com.wiliamks.temaqui.commons.toBRL
import com.wiliamks.temaqui.commons.toDp
import com.wiliamks.temaqui.commons.viewmodel.ViewState
import com.wiliamks.temaqui.databinding.ActivityProductDetailsBinding
import com.wiliamks.temaqui.databinding.BottomSheetBuyDialogBinding
import com.wiliamks.temaqui.ui.cart.activity.CartActivity
import com.wiliamks.temaqui.ui.detail.adapter.PicturesListAdapter
import com.wiliamks.temaqui.ui.detail.adapter.SliderAdapter
import com.wiliamks.temaqui.ui.detail.repository.DetailsRepositoryImpl
import com.wiliamks.temaqui.ui.detail.viewmodel.DetailsViewModel
import com.wiliamks.temaqui.ui.personalization.activity.PersonalizationActivity

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private var quantity = 1
    private val viewModel by viewModels<DetailsViewModel> {
        val repository = DetailsRepositoryImpl()
        DetailsViewModel.Factory(Session.currentSelectedProduct!!, repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Session.currentSelectedProduct?.let {
            configureItem(it)
            configureViewModel()
        }
    }

    private fun configureViewModel() {
        viewModel.viewState.observe(this) {
            when (it) {
                ViewState.Error -> Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show()
                ViewState.Loading -> {}
                ViewState.Success ->  {
                    showBottomSheet()
                }
            }
        }
    }

    private fun showBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetBindind = BottomSheetBuyDialogBinding.inflate(layoutInflater, null, false)

        bottomSheetBindind.imgProduct.setImageResource(Session.currentSelectedProduct!!.pictures[0])
        bottomSheetBindind.textProductName.text = Session.currentSelectedProduct!!.name

        bottomSheetBindind.buttonGoCart.setOnClickListener {
            bottomSheetDialog.dismiss()

            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        bottomSheetDialog.setContentView(bottomSheetBindind.root)
        bottomSheetDialog.show()
    }

    private fun configureItem(item: Product) {
        binding.sliderProduct.setSliderAdapter(SliderAdapter(item.pictures))
        binding.textName.text = item.name
        binding.textPrice.text = item.price.toBRL()
        binding.textScore.text = item.score.toString()
        binding.textNumberReviews.text = getString(R.string.details_review_content, item.reviewsCount)
        binding.textQuantity.text = quantity.toString()
        binding.textSoldBy.text = Html.fromHtml(
            getString(R.string.details_hyperlink, item.originalLink, item.soldBy),
            Html.FROM_HTML_MODE_COMPACT
        )
        binding.buttonPlus.setOnClickListener {
            quantity++
            binding.textQuantity.text = quantity.toString()
        }
        binding.buttonMinus.setOnClickListener {
            if (quantity > 1) quantity--
            binding.textQuantity.text = quantity.toString()
        }

        item.sizesAvailable.forEachIndexed { index, it ->
            val button = RadioButton(this).apply {
                setBackgroundResource(R.drawable.radio_button_background)
                buttonDrawable = null
                text = it
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                val margin = 8.toDp(context)
                val size = 45.toDp(context)
                val params = ConstraintLayout.LayoutParams(size, size)
                if (index != 0) params.marginStart = margin
                if (index != item.sizesAvailable.size - 1) params.marginEnd = margin
                layoutParams = params
            }

            binding.radioSize.addView(button)
        }

        binding.recyclerListImages.adapter = PicturesListAdapter(item.pictures, 0) {
            binding.sliderProduct.currentPagePosition = it
        }
        binding.recyclerListImages.addItemDecoration(PicturesListAdapter.HorizontalMarginItemDecoration)

        binding.sliderProduct.setCurrentPageListener {
            (binding.recyclerListImages.adapter as PicturesListAdapter).changePosition(it)
        }

        binding.buttonAdd.setOnClickListener {
            if (binding.radioSize.checkedRadioButtonId == -1)
                Toast.makeText(this, "Selecione um tamanho", Toast.LENGTH_SHORT).show()
            else
                viewModel.addItemToCart(quantity, binding.radioSize.findViewById<RadioButton>(binding.radioSize.checkedRadioButtonId).text.toString())
        }

        binding.buttonPersonalize.setOnClickListener {
            val intent = Intent(this, PersonalizationActivity::class.java)
            startActivity(intent)
        }
    }
}