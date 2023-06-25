package com.wiliamks.temaqui.commons.model

data class Product(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val score: Double = 0.0,
    val soldBy: String = "",
    val originalLink: String = "",
    val reviewsCount: Int = 0,
    val pictures: ArrayList<Int> = arrayListOf(),
    val sizesAvailable: ArrayList<String> = arrayListOf(),
    val onCart: Boolean = false,
    val liked: Boolean = false
)
