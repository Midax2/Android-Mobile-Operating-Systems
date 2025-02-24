package com.pg.lab1

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var calculateButton: Button
    private lateinit var resetButton: Button
    private lateinit var valueToCalculateEditText: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var mainTextView: TextView
    private lateinit var enterValueTextView: TextView
    private lateinit var resultTextView: TextView
    private lateinit var resultValueTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeViews()
        showInitialView()
        setupCalculateButton(calculateButton)
        setupResetButton(resetButton)
    }

    private fun initializeViews() {
        calculateButton = findViewById(R.id.CalculationButton)
        resetButton = findViewById(R.id.ResetButton)
        valueToCalculateEditText = findViewById(R.id.ValueToCalculate)
        progressBar = findViewById(R.id.progressBar)
        mainTextView = findViewById(R.id.MainText)
        enterValueTextView = findViewById(R.id.EnterValue)
        resultTextView = findViewById(R.id.ResultText)
        resultValueTextView = findViewById(R.id.ResultValue)
    }

    @SuppressLint("SetTextI18n")
    private fun setupCalculateButton(calculateButton: Button) {
        calculateButton.setOnClickListener {
            val valueToCalculate = valueToCalculateEditText.text.toString()
            if (valueToCalculate.isNotEmpty()) {
                changeToCalculationView()
                val number = valueToCalculate.toInt()
                lifecycleScope.launch {
                    Log.d("MainActivity", "Calculating factorial for $number")
                    try {
                        progressBar.progress = 0
                        val result = FactorialCalculator.calculateFactorial(number) {
                            launch(Dispatchers.Main) {
                                progressBar.progress = it
                                resultValueTextView.text = "Current progress: $it%"
                            }
                        }
                        showResult(result, number)
                    } catch (e: IllegalArgumentException) {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.Error_occurred) + e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } else {
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupResetButton(resetButton: Button) {
        resetButton.setOnClickListener {
            showInitialView()
        }
    }

    // Hide initial views and show progress bar and result of calculation
    private fun changeToCalculationView() {
        enterValueTextView.visibility = TextView.GONE
        valueToCalculateEditText.visibility = EditText.GONE
        calculateButton.visibility = Button.GONE
        resultTextView.visibility = TextView.VISIBLE
        resultValueTextView.visibility = TextView.VISIBLE
        progressBar.visibility = ProgressBar.VISIBLE
    }

    private fun showResult(result: Long, valueToCalculate: Int) {
        resetButton.visibility = Button.VISIBLE
        resultTextView.text = getString(R.string.calculation_finished, valueToCalculate)
        resultValueTextView.text = String.format(result.toString())
    }

    private fun showInitialView() {
        enterValueTextView.visibility = TextView.VISIBLE
        valueToCalculateEditText.visibility = EditText.VISIBLE
        valueToCalculateEditText.text.clear()
        calculateButton.visibility = Button.VISIBLE
        resultTextView.visibility = TextView.GONE
        resultTextView.text = getString(R.string.calculation_in_progress)
        resultValueTextView.visibility = TextView.GONE
        resultValueTextView.text = ""
        progressBar.visibility = ProgressBar.GONE
        resetButton.visibility = Button.GONE
    }
}