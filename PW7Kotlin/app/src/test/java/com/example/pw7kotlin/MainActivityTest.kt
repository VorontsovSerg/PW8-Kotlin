package com.example.pw7kotlin

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.pw7kotlin.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    // Проверка, что поле для ввода URL отображается
    @Test
    fun testEditTextIsDisplayed() {
        onView(withId(R.id.editTextUrl)).check(matches(isDisplayed()))
    }

    // Проверка, что кнопка загрузки изображения отображается
    @Test
    fun testButtonIsDisplayed() {
        onView(withId(R.id.buttonLoadImage)).check(matches(isDisplayed()))
    }

    // Проверка, что ImageView отображается после нажатия на кнопку
    @Test
    fun testImageViewIsDisplayedAfterButtonClick() {
        onView(withId(R.id.buttonLoadImage)).perform(click())
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }

    // Проверка, что текст в EditText сохраняется при нажатии кнопки
    @Test
    fun testButtonClickWithInputUrl() {
        onView(withId(R.id.editTextUrl)).perform(typeText("https://example.com/image.jpg"), closeSoftKeyboard())
        onView(withId(R.id.buttonLoadImage)).perform(click())
        onView(withId(R.id.editTextUrl)).check(matches(withText("https://example.com/image.jpg")))
    }

    // Проверка, что при вводе текста в EditText и нажатии кнопки отображается изображение (заглушка)
    @Test
    fun testLoadImageBehavior() {
        onView(withId(R.id.editTextUrl)).perform(typeText("https://example.com/image.jpg"), closeSoftKeyboard())
        onView(withId(R.id.buttonLoadImage)).perform(click())
        // Проверка, что ImageView отображает изображение (тест заглушка, фактическая загрузка проверяется в функциональных тестах)
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }
}
