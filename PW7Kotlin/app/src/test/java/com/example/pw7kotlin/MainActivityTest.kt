package com.example.pw7kotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.runBlocking
import org.robolectric.Robolectric
import org.robolectric.annotation.Config
import java.io.File
import java.io.FileOutputStream

@Config(sdk = [28])
class MainActivityTest {

    private lateinit var mainActivity: MainActivity
    private lateinit var testBitmap: Bitmap

    fun setup() {
        mainActivity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        testBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    }

    fun testSaveImageToInternalStorage() {
        setup()

        val file = File(mainActivity.filesDir, "test_image.jpg")
        FileOutputStream(file).use { out ->
            testBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        if (!file.exists()) throw AssertionError("Image should be saved to internal storage")
        file.delete()
    }

    fun testLoadImageFromInternalStorage() {
        setup()

        val file = File(mainActivity.filesDir, "test_image.jpg")
        FileOutputStream(file).use { out ->
            testBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        val loadedBitmap = BitmapFactory.decodeFile(file.absolutePath)
        if (loadedBitmap == null) throw AssertionError("Loaded bitmap should not be null")
        file.delete()
    }

    fun testDownloadImageHandlesNullURL() {
        setup()

        runBlocking {
            val url = ""
            val bitmap = mainActivity.downloadImage(url)
            if (bitmap != null) throw AssertionError("Bitmap should be null for empty URL")
        }
    }

    fun testFileNameIsCorrect() {
        setup()

        val fileName = mainActivity.imageFileName
        if (fileName != "downloaded_image.jpg") throw AssertionError("File name should be downloaded_image.jpg")
    }

    fun testEmptyURLDoesNotTriggerDownload() {
        setup()

        runBlocking {
            val emptyUrl = ""
            val bitmap = mainActivity.downloadImage(emptyUrl)
            if (bitmap != null) throw AssertionError("Bitmap should be null if URL is empty")
        }
    }
}
