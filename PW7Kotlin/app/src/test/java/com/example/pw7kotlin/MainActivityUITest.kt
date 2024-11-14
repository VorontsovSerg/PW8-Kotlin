package com.example.pw7kotlin

import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.kotlin.test.assertTrue
import org.robolectric.Robolectric
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.BeforeTest
import kotlin.test.Test

@Config(sdk = [28]) // Указываем SDK для тестирования
class MainActivityUITest {

    private lateinit var mainActivity: MainActivity
    private lateinit var buttonLoadImage: Button
    private lateinit var editTextUrl: EditText
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView

    @BeforeTest
    fun setup() {
        mainActivity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        buttonLoadImage = mainActivity.findViewById(R.id.buttonLoadImage)
        editTextUrl = mainActivity.findViewById(R.id.editTextUrl)
        imageView = mainActivity.findViewById(R.id.imageView)
        textView = mainActivity.findViewById(R.id.textView)
    }

    @Test
    fun `test imageView is visible`() {
        assertTrue { imageView.visibility == android.view.View.VISIBLE }
    }

    @Test
    fun `test button click triggers load image`() {
        editTextUrl.setText("https://example.com/image.jpg")
        buttonLoadImage.performClick()
        assertTrue { mainActivity.imageView.drawable != null }
    }

    @Test
    fun `test editText is empty on start`() {
        assertTrue { editTextUrl.text.isEmpty() }
    }

    @Test
    fun `test textView updates with editText content`() {
        editTextUrl.setText("Hello, Kotlin!")
        buttonLoadImage.performClick()
        assertTrue { textView.text.toString() == "Hello, Kotlin!" }
    }

    @Test
    fun `test button not clickable with empty URL`() {
        editTextUrl.setText("")
        buttonLoadImage.performClick()
        assertTrue { mainActivity.imageView.drawable == null }
    }
}
