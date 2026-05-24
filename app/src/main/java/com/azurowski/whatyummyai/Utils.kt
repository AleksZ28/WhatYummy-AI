package com.azurowski.whatyummyai

import java.util.Locale.getDefault

fun String.capitalizeFirst(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            getDefault()
        ) else it.toString()
    }
}

fun formatAmount(amount: Double): String {
    return if (amount % 1.0 == 0.0) {
        amount.toInt().toString()
    } else {
        String.format(getDefault(), "%.2f", amount)
    }
}