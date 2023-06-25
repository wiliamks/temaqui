package com.wiliamks.temaqui.commons

import android.content.Context
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt


fun Double.toBRL(): String
    = NumberFormat.getCurrencyInstance(Locale("pt", "br")).format(this)

fun Int.toDp(context: Context): Int
    = (this * context.resources.displayMetrics.density).roundToInt()