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

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class MainActivityUITest {

    private lateinit var activity: MainActivity

    @Before
    fun setUp() {
        activity = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .get()
    }

    @Test
    fun `проверка, что кнопка загрузки существует`() {
        val buttonLoadImage = activity.findViewById<Button>(R.id.buttonLoadImage)
        assertNotNull("Кнопка загрузки должна быть найдена", buttonLoadImage)
    }

    @Test
    fun `проверка клика по кнопке загрузки`() {
        val editTextUrl = activity.findViewById<EditText>(R.id.editTextUrl)
        val buttonLoadImage = activity.findViewById<Button>(R.id.buttonLoadImage)

        editTextUrl.setText("https://sun9-1.userapi.com/impg/e922V3g-41UwpTV_Baop1ywLIFr9p28gQxBdYw/fEXjc3nZMhY.jpg")
        buttonLoadImage.performClick()

        assertEquals(
            "Текстовое поле должно содержать URL",
            "https://sun9-1.userapi.com/impg/e922V3g-41UwpTV_Baop1ywLIFr9p28gQxBdYw/fEXjc3nZMhY.jpg",
            editTextUrl.text.toString()
        )
    }

    @Test
    fun `проверка, что изображение отображается`() {
        val imageView = activity.findViewById<ImageView>(R.id.imageView)
        assertNotNull("Изображение должно быть найдено", imageView)
    }

    @Test
    fun `проверка реакции на пустой URL`() {
        val editTextUrl = activity.findViewById<EditText>(R.id.editTextUrl)
        val buttonLoadImage = activity.findViewById<Button>(R.id.buttonLoadImage)

        editTextUrl.setText("")
        buttonLoadImage.performClick()

        assertEquals(
            "Если URL пуст, текстовое поле останется пустым",
            "",
            editTextUrl.text.toString()
        )
    }

    @Test
    fun `проверка загрузки по корректному URL`() {
        val editTextUrl = activity.findViewById<EditText>(R.id.editTextUrl)
        val buttonLoadImage = activity.findViewById<Button>(R.id.buttonLoadImage)

        editTextUrl.setText("https://example.com/image.jpg")
        buttonLoadImage.performClick()

        val imageView = activity.findViewById<ImageView>(R.id.imageView)
        assertNotNull("Изображение должно обновиться", imageView.drawable)
    }
}
