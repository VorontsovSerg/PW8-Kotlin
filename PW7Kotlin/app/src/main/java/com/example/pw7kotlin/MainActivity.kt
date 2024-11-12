package com.example.pw7kotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var editTextUrl: EditText
    private lateinit var buttonLoadImage: Button
    private lateinit var imageView: ImageView

    private val imageFileName = "downloaded_image.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextUrl = findViewById(R.id.editTextUrl)
        buttonLoadImage = findViewById(R.id.buttonLoadImage)
        imageView = findViewById(R.id.imageView)

        checkIfImageExistsAndLoad()

        buttonLoadImage.setOnClickListener {
            val url = editTextUrl.text.toString()
            if (url.isNotEmpty()) {
                loadImage(url)
            } else {
                Toast.makeText(this, "Введите ссылку", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Проверка, есть ли сохраненное изображение
    private fun checkIfImageExistsAndLoad() {
        lifecycleScope.launch(Dispatchers.IO) {
            val file = File(filesDir, imageFileName)
            if (file.exists()) {
                val bitmap = loadImageFromInternalStorage()
                withContext(Dispatchers.Main) {
                    imageView.setImageBitmap(bitmap)
                    Toast.makeText(this@MainActivity, "Изображение загружено из памяти", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadImage(url: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val bitmap = downloadImage(url)

                if (bitmap != null) {
                    saveImageToInternalStorage(bitmap)

                    withContext(Dispatchers.Main) {
                        imageView.setImageBitmap(bitmap)
                        Toast.makeText(this@MainActivity, "Изображение загружено и сохранено", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Не удалось загрузить изображение", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Загрузка изображения с URL с использованием Glide
    private suspend fun downloadImage(url: String): Bitmap? {
        return try {
            Glide.with(this)
                .asBitmap()
                .load(url)
                .submit()
                .get()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Сохранение изображения во внутренней памяти устройства
    private fun saveImageToInternalStorage(bitmap: Bitmap) {
        try {
            val file = File(filesDir, imageFileName)
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Загрузка изображения из внутренней памяти
    private fun loadImageFromInternalStorage(): Bitmap {
        val file = File(filesDir, imageFileName)
        return BitmapFactory.decodeFile(file.absolutePath)
    }
}
