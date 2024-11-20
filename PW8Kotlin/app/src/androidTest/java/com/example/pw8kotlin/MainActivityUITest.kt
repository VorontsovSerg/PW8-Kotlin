package com.example.pw8kotlin

import android.os.SystemClock.sleep
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityUITest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testEmptyInputField() {
        // Проверка поведения при пустом поле URL
        onView(withId(R.id.editTextUrl)).perform(typeText(""), closeSoftKeyboard())
        onView(withId(R.id.buttonLoadImage)).perform(click())
        sleep(1000)
        // Проверяем, что ImageView всё ещё скрыт
        onView(withId(R.id.imageView)).check(matches(not(isDisplayed())))
    }

    @Test
    fun editTextTextTest() {
        // Ввод значения
        onView(withId(R.id.editTextUrl)).perform(typeText("input"), closeSoftKeyboard())
    }

    @Test
    fun defaultImageBtn() {
        // Проверка, что при нажатии кнопки imageView не ломается
        onView(withId(R.id.buttonLoadImage)).perform(click())
        sleep(5000)
        onView(withId(R.id.imageView)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testImageLoadSuccess() {
        // Ввод корректной ссылки в EditText
        onView(withId(R.id.editTextUrl))
            .perform(typeText("https://static.sobaka.ru/uploads/nsk/1.jpg"), closeSoftKeyboard())

        // Нажатие на кнопку
        onView(withId(R.id.buttonLoadImage)).perform(click())
        sleep(5000)
        // Проверка, что изображение загружено в ImageView
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }

    @Test
    fun testImageLoadFailure() {
        // Ввод некорректной ссылки в EditText
        onView(withId(R.id.editTextUrl))
            .perform(typeText("https://static.sobaka.ru/uploads/nsk/1"), closeSoftKeyboard())

        onView(withId(R.id.buttonLoadImage)).perform(click())

        // Проверка, что изображение не изменилось в ImageView
        onView(withId(R.id.imageView)).check(matches(not(isDisplayed())))
    }
}
