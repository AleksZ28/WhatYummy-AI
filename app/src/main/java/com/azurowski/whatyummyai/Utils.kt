package com.azurowski.whatyummyai

import android.content.Context

fun dpToPx(dp: Int, context: Context): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
}