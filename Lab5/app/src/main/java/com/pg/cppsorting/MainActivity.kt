package com.pg.cppsorting

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random
import kotlin.system.measureNanoTime

class MainActivity : AppCompatActivity() {

    private lateinit var outputView: TextView
    private var randomArray = IntArray(0)

    private external fun sortArray(array: IntArray)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        outputView = findViewById(R.id.outputView)

        findViewById<Button>(R.id.generateButton).setOnClickListener {
            generateArray()
        }

        findViewById<Button>(R.id.sortKotlinButton).setOnClickListener {
            sortInKotlin()
        }

        findViewById<Button>(R.id.sortCppButton).setOnClickListener {
            sortInCpp()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun generateArray() {
        randomArray = IntArray(100) { Random.nextInt(0, 1000) }
        outputView.text = "Generated Array:\n${randomArray.joinToString(", ")}"
    }

    @SuppressLint("SetTextI18n")
    private fun sortInKotlin() {
        val copy = randomArray.copyOf()
        val time = measureNanoTime {
            copy.sort()
        }
        outputView.text = "Kotlin Sorted Array (ASC):\n${copy.joinToString(", ")}\n\nTime: $time ns"
    }

    @SuppressLint("SetTextI18n")
    private fun sortInCpp() {
        val copy = randomArray.copyOf()
        val time = measureNanoTime {
            sortArray(copy)
        }
        outputView.text = "C++ Sorted Array (ASC):\n${copy.joinToString(", ")}\n\nTime: $time ns"
    }

    companion object {
        // Used to load the 'cppsorting' library on application startup.
        init {
            System.loadLibrary("cppsorting")
        }
    }
}