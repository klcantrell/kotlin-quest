package com.example.ageinminutes

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.ageinminutes.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonDatePicker.setOnClickListener { view ->
            clickDatePicker(view)
        }
    }

    private fun clickDatePicker(view: View) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { datePickerView, selectedYear, selectedMonth, selectedDayOfMonth ->
                val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.US)
                val date =
                    formatter.parse("${selectedMonth + 1}/$selectedDayOfMonth/$selectedYear")!!
                val selectedDateInMinutes = date.time / 60_000
                val currentDateInMinutes = Date().time / 60_000
                binding.tvSelectedDate.text = formatter.format(date)
                binding.tvSelectedDateInMinutes.text =
                    (currentDateInMinutes - selectedDateInMinutes).toString()
            },
            year,
            month,
            dayOfMonth
        )

        datePickerDialog.datePicker.maxDate = Date().time.minus(86400000)
        datePickerDialog.show()
    }
}
