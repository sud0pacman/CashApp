package com.sudo_pacman.cashapp.core.utils

import java.text.DecimalFormat
import java.util.Locale

fun Double.formatWithCommasAndDecimals(): String {
    val formatter = DecimalFormat("#,##0.00")
    formatter.decimalFormatSymbols = formatter.decimalFormatSymbols.apply {
        groupingSeparator = ','
        decimalSeparator = '.'
    }
    return formatter.format(this)
}

fun clearCardNumber(cardNumber: String): String {
    return cardNumber.filter { it.isDigit() };
}