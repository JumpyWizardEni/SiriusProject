package com.siriusproject.coshelek.utils

import android.graphics.Color

fun darken(color: Int, factor: Float): Int {
    val a: Int = Color.alpha(color)
    val r = Math.round(Color.red(color) * factor).toInt()
    val g = Math.round(Color.green(color) * factor).toInt()
    val b = Math.round(Color.blue(color) * factor).toInt()
    return Color.argb(
        a,
        Math.min(r, 255),
        Math.min(g, 255),
        Math.min(b, 255)
    )
}