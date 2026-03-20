package com.azurowski.whatyummyai

import java.util.Locale.getDefault

fun String.capitalizeFirst(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            getDefault()
        ) else it.toString()
    }
}