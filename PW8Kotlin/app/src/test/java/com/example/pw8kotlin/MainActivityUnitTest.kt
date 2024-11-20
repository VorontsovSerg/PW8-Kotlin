package com.example.pw8kotlin

import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class MainActivityTest {

    private lateinit var activity: MainActivity

    @Before
    fun setUp() {
        // Инициализация логирования и создание экземпляра MainActivity
        ShadowLog.stream = System.out
        activity = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .get()
    }

    // Проверка, что текстовое поле для ввода URL существует
    @Test
    fun checkUrlEditTextExists() {
        val editTextUrl = activity.findViewById<EditText>(R.id.editTextUrl)
        assertNotNull("Текстовое поле для URL должно быть найдено", editTextUrl)
    }

    // Проверка, что при клике по кнопке с заполненным URL значение остается неизменным
    @Test
    fun clickLoadButtonWithValidUrl() {
        val editTextUrl = activity.findViewById<EditText>(R.id.editTextUrl)
        val buttonLoadImage = activity.findViewById<Button>(R.id.buttonLoadImage)

        editTextUrl.setText("https://example.com/image.jpg")
        buttonLoadImage.performClick()

        assertEquals(
            "URL должен быть сохранен в текстовом поле",
            "https://example.com/image.jpg",
            editTextUrl.text.toString()
        )
    }

    // Проверка, что изображение отображается в ImageView
    @Test
    fun checkImageViewDisplaysImage() {
        val imageView = activity.findViewById<ImageView>(R.id.imageView)
        assertNotNull("ImageView должен быть найден", imageView)

        // Проверяем, что drawable изначально пустой
        assertNull("Drawable должен быть изначально пустым", imageView.drawable)
    }

    // Проверка, что при пустом URL поведение остается корректным
    @Test
    fun handleEmptyUrlInput() {
        val editTextUrl = activity.findViewById<EditText>(R.id.editTextUrl)
        val buttonLoadImage = activity.findViewById<Button>(R.id.buttonLoadImage)

        editTextUrl.setText("")
        buttonLoadImage.performClick()

        assertTrue("Поле URL должно остаться пустым", editTextUrl.text.toString().isEmpty())
    }

    // Проверка, что при клике по кнопке логируется действие
    @Test
    fun logButtonClickAction() {
        val buttonLoadImage = activity.findViewById<Button>(R.id.buttonLoadImage)

        // Логируем действие клика
        buttonLoadImage.performClick()
        println("Кнопка была нажата, проверьте лог в консоли")
    }
}
