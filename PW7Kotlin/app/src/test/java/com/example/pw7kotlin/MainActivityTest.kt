package com.example.pw7kotlin

import android.graphics.Bitmap
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var mainActivity: MainActivity
    private lateinit var testBitmap: Bitmap

    @Before
    fun setUp() {
        mainActivity = MainActivity()
        testBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    }

    @After
    fun tearDown() {
        // Очищаем ресурсы после теста
        val file = File(mainActivity.filesDir, "downloaded_image.jpg")
        if (file.exists()) {
            file.delete()
        }
    }

    @Test
    fun testSaveImageToInternalStorage() {
        mainActivity.saveImageToInternalStorage(testBitmap)
        val file = File(mainActivity.filesDir, "downloaded_image.jpg")
        assertTrue("Image was not saved", file.exists())
    }

    @Test
    fun testLoadImageFromInternalStorage() {
        // Сначала сохраняем изображение, чтобы проверить его загрузку
        mainActivity.saveImageToInternalStorage(testBitmap)
        val loadedBitmap = mainActivity.loadImageFromInternalStorage()
        assertNotNull("Image was not loaded", loadedBitmap)
        assertEquals("Loaded bitmap width mismatch", testBitmap.width, loadedBitmap!!.width)
        assertEquals("Loaded bitmap height mismatch", testBitmap.height, loadedBitmap.height)
    }

    @Test
    fun testDownloadImageSuccess() = runTest {
        val url = "https://example.com/image.jpg"  // Укажите действительный URL изображения
        val bitmap = mainActivity.downloadImage(url)
        assertNotNull("Image was not downloaded", bitmap)
    }

    @Test
    fun testDownloadImageFailure() = runTest {
        val url = "https://invalid-url.com"
        val bitmap = mainActivity.downloadImage(url)
        assertNull("Image download should fail for invalid URL", bitmap)
    }

    @Test
    fun testImageExistsLocally() {
        mainActivity.saveImageToInternalStorage(testBitmap)
        val file = File(mainActivity.filesDir, "downloaded_image.jpg")
        assertTrue("Image file does not exist locally", file.exists())
    }
}
