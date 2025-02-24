package com.pg.lab1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val calculateButton = findViewById<Button>(R.id.CalculationButton)
        setupCalculateButton(calculateButton)
    }

    private fun setupCalculateButton(calculateButton: Button) {
        calculateButton.setOnClickListener {
            changeToCalculationView()
            val valueToCalculate = findViewById<EditText>(R.id.ValueToCalculate).text.toString()
            if (valueToCalculate.isNotEmpty()) {
                val number = valueToCalculate.toInt()
                lifecycleScope.launch {
                    Log.d("MainActivity", "Calculating factorial for $number")
                    try {
                        val result = FactorialCalculator.calculateFactorial(number)
                        findViewById<TextView>(R.id.MainText).text = String.format(result.toString())
                    } catch (e: IllegalArgumentException) {
                        Toast.makeText(this@MainActivity,
                            R.string.Error_occurred.toString()
                                    + e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Hide initial views and show progress bar and result of calculation
    private fun changeToCalculationView() {
        findViewById<TextView>(R.id.EnterValue).visibility = TextView.GONE
        findViewById<EditText>(R.id.ValueToCalculate).visibility = EditText.GONE
        findViewById<Button>(R.id.CalculationButton).visibility = Button.GONE
        findViewById<TextView>(R.id.ResultText).visibility = TextView.VISIBLE
    }
}