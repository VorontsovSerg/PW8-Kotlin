package com.example.pw7kotlin

import android.graphics.Bitmap

class ImageUtils {
    fun isValidUrl(url: String): Boolean {
        return url.startsWith("http://") || url.startsWith("https://")
    }

    fun calculateImageSize(bitmap: Bitmap): Int {
        return bitmap.byteCount
    }

    fun addNumbers(a: Int, b: Int): Int {
        return a + b
    }
}
