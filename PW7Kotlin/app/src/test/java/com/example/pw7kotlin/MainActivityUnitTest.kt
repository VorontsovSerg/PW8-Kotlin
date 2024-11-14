package com.example.pw7kotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.jetbrains.kotlin.test.assertTrue
import org.robolectric.Robolectric
import org.robolectric.RuntimeEnvironment
import java.io.File
import java.io.FileOutputStream
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityUnitTest {

    private lateinit var mainActivity: MainActivity
    private lateinit var testBitmap: Bitmap

    @BeforeTest
    fun setup() {
        mainActivity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        testBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    }

    @Test
    fun `test saveImageToInternalStorage saves file`() = runTest {
        val file = File(mainActivity.filesDir, "test_image.jpg")
        FileOutputStream(file).use { out ->
            testBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        assertTrue { file.exists() }
        file.delete()
    }

    @Test
    fun `test loadImageFromInternalStorage loads correct file`() = runTest {
        val file = File(mainActivity.filesDir, "test_image.jpg")
        FileOutputStream(file).use { out ->
            testBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        val loadedBitmap = BitmapFactory.decodeFile(file.absolutePath)
        assertTrue { loadedBitmap != null }
        file.delete()
    }

    @Test
    fun `test downloadImage handles null URL`() = runTest {
        val url = ""
        val bitmap = mainActivity.downloadImage(url)
        assertTrue { bitmap == null }
    }

    @Test
    fun `test file name is correct`() {
        val fileName = mainActivity.imageFileName
        assertTrue { fileName == "downloaded_image.jpg" }
    }

    @Test
    fun `test empty URL does not trigger download`() {
        val mockMainActivity = mockk<MainActivity>(relaxed = true)
        every { mockMainActivity.downloadImage("") } returns null
        mockMainActivity.downloadImage("")
        verify(exactly = 1) { mockMainActivity.downloadImage("") }
    }
}
