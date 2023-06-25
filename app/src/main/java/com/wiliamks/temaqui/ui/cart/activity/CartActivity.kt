package com.wiliamks.temaqui.ui.cart.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.wiliamks.temaqui.MainActivity
import com.wiliamks.temaqui.commons.Session
import com.wiliamks.temaqui.commons.toBRL
import com.wiliamks.temaqui.commons.viewmodel.ViewState
import com.wiliamks.temaqui.databinding.ActivityCartBinding
import com.wiliamks.temaqui.ui.cart.adapter.CartAdapter
import com.wiliamks.temaqui.ui.cart.repository.CartRepositoryImpl
import com.wiliamks.temaqui.ui.cart.viewmodel.CartViewModel
import com.wiliamks.temaqui.ui.cart.viewmodel.CartViewState

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private val viewModel by viewModels<CartViewModel> {
        val repository = CartRepositoryImpl()
        CartViewModel.Factory(repository, Session.cartSelection)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureRecycler()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.viewState.observe(this) {
            when (it) {
                ViewState.Error -> Unit
                ViewState.Loading -> Unit
                ViewState.Success -> Unit
            }
        }

        viewModel.cartViewState.observe(this) {
            when (it) {
                is CartViewState.UpdateCoupon -> {
                    binding.textCouponContent.text = it.value.toBRL()
                }
                is CartViewState.UpdateDelivery -> {
                    binding.textDeliveryContent.text = it.delivery.toBRL()
                }
                is CartViewState.UpdateSubTotalValue -> {
                    binding.textSubtotalContent.text = it.subTotal.toBRL()
                }
                is CartViewState.UpdateTotalValue -> {
                    binding.textTotalContent.text = it.total.toBRL()
                }
                is CartViewState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.getDeliveryValue()
    }

    private fun configureRecycler() {
        binding.recyclerCart.adapter = CartAdapter(Session.cartSelection) {
            viewModel.updateItem(cartItem = it)
        }
        binding.recyclerCart.addItemDecoration(CartAdapter.VerticalMarginItemDecoration)

        binding.editCoupon.setOnEditorActionListener { _, actionId, keyEvent ->
            if (
                keyEvent != null && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER ||
                actionId == EditorInfo.IME_ACTION_DONE
            ) {
                binding.btnCoupon.performClick()
            }
            false
        }

        binding.btnCoupon.setOnClickListener {
            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }

            viewModel.updateCoupon(binding.editCoupon.text.toString())

            binding.editCoupon.text.clear()
        }

        binding.buttonArrowBack.setOnClickListener {
            comebackToHome()
        }
    }

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() = comebackToHome()
    }

    private fun comebackToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}