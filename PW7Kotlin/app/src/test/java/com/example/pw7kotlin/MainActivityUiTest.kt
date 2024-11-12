package com.example.pw7kotlin

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class MainActivityUiTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testEditTextInput() {
        // Проверяем, что EditText позволяет ввод текста
        onView(withId(R.id.editTextUrl))
            .perform(typeText("https://example.com/image.jpg"), closeSoftKeyboard())
            .check(matches(withText("https://example.com/image.jpg")))
    }

    @Test
    fun testButtonClickLoadsImage() {
        // Вводим URL и нажимаем на кнопку загрузки изображения
        onView(withId(R.id.editTextUrl))
            .perform(typeText("https://example.com/image.jpg"), closeSoftKeyboard())
        onView(withId(R.id.buttonLoadImage)).perform(click())

        // Проверка, что изображение отображается
        onView(withId(R.id.imageView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testEmptyUrlError() {
        // Нажимаем на кнопку без ввода URL
        onView(withId(R.id.buttonLoadImage)).perform(click())

        // Проверка, что появилось сообщение об ошибке
        onView(withText("Введите ссылку"))
            .inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testImageLoadedFromStorage() {
        // Проверка, что при перезапуске активити изображение отображается (если оно было сохранено ранее)
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }

    @Test
    fun testToastMessageOnSuccessfulLoad() {
        // Вводим корректный URL, нажимаем на кнопку загрузки
        onView(withId(R.id.editTextUrl))
            .perform(typeText("https://example.com/image.jpg"), closeSoftKeyboard())
        onView(withId(R.id.buttonLoadImage)).perform(click())

        // Проверяем, что появляется Toast с сообщением об успешной загрузке
        onView(withText("Изображение загружено и сохранено"))
            .inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView))))
            .check(matches(isDisplayed()))
    }
}
