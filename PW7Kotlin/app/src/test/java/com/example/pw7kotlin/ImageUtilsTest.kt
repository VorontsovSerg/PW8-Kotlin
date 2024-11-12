package com.example.pw7kotlin

import android.graphics.Bitmap
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ImageUtilsTest {

    private lateinit var utils: ImageUtils

    @Before
    fun setUp() {
        utils = ImageUtils()
    }

    @Test
    fun testIsValidUrl() {
        assertTrue(utils.isValidUrl("http://example.com"))
        assertTrue(utils.isValidUrl("https://example.com"))
        assertFalse(utils.isValidUrl("ftp://example.com"))
        assertFalse(utils.isValidUrl("example.com"))
    }

    @Test
    fun testCalculateImageSize() {
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        assertEquals(40000, utils.calculateImageSize(bitmap))
    }

    @Test
    fun testAddition() {
        assertEquals(5, utils.addNumbers(2, 3))
        assertEquals(-1, utils.addNumbers(-2, 1))
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInvalidAddition() {
        utils.addNumbers(Integer.MAX_VALUE, 1)
    }

    @Test(timeout = 1000)
    fun testPerformance() {
        val bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)
        utils.calculateImageSize(bitmap)
    }
}
