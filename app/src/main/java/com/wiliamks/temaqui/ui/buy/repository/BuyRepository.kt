package com.wiliamks.temaqui.ui.buy.repository

import com.wiliamks.temaqui.commons.model.Category
import com.wiliamks.temaqui.commons.model.Product

interface BuyRepository {
    fun getCategories(): ArrayList<Category>

    fun getRecommendations(userId: Int): ArrayList<Product>
}