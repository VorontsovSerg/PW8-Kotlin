package com.example.pw7kotlin

import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import org.robolectric.Robolectric
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

@Config(sdk = [28])
class MainActivityUITest {

    private lateinit var mainActivity: MainActivity
    private lateinit var buttonLoadImage: Button
    private lateinit var editTextUrl: EditText
    private lateinit var imageView: ImageView

    // Инициализация перед тестами
    fun setup() {
        mainActivity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        buttonLoadImage = mainActivity.findViewById(R.id.buttonLoadImage)
        editTextUrl = mainActivity.findViewById(R.id.editTextUrl)
        imageView = mainActivity.findViewById(R.id.imageView)
    }

    // Тест 1: Проверка, что EditText и Button видимы
    fun testEditTextAndButtonAreVisible() {
        setup()

        if (editTextUrl.visibility != android.view.View.VISIBLE) throw AssertionError("EditText should be visible")
        if (buttonLoadImage.visibility != android.view.View.VISIBLE) throw AssertionError("Button should be visible")
    }

    // Тест 2: Проверка, что ImageView видим
    fun testImageViewIsVisible() {
        setup()

        if (imageView.visibility != android.view.View.VISIBLE) throw AssertionError("ImageView should be visible")
    }

    // Тест 3: Проверка, что Toast отображается при пустом URL
    fun testButtonDoesNotLoadImageWithEmptyURL() {
        setup()

        editTextUrl.setText("")
        buttonLoadImage.performClick()
        val latestToast: Toast = ShadowToast.getLatestToast()
        val toastText = ShadowToast.getTextOfLatestToast()
        if (latestToast.view == null || toastText != "Введите ссылку") {
            throw AssertionError("Toast with message 'Введите ссылку' should be shown for empty URL")
        }
    }

    // Тест 4: Проверка, что EditText пустой при старте
    fun testEditTextIsEmptyOnStart() {
        setup()

        if (editTextUrl.text.isNotEmpty()) throw AssertionError("EditText should be empty on start")
    }

    // Тест 5: Проверка загрузки изображения при валидном URL
    fun testButtonLoadsImageWithValidURL() {
        setup()

        editTextUrl.setText("https://example.com/image.jpg")
        buttonLoadImage.performClick()
        if (mainActivity.imageView.drawable == null) throw AssertionError("ImageView should display loaded image")
    }
}
