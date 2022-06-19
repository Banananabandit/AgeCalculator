package android.banananabandit.agecalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    val units = arrayOf<String?>("Days", "Hours", "Minutes")
    private lateinit var textViewMeasureUnits : TextView
    private lateinit var textViewBirthdayUnits : TextView
    private lateinit var textViewDateSelected : TextView
    private lateinit var textViewAgeDisplay : TextView

    private var chosenUnits : String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewMeasureUnits = findViewById(R.id.age_measure_unit)
        textViewBirthdayUnits = findViewById(R.id.birthday_measure_unit)
        textViewDateSelected = findViewById(R.id.date_selected)
        textViewAgeDisplay = findViewById(R.id.age_display)

        val buttonDatePicker = findViewById<Button>(R.id.button_age_selection)
        val spinner = findViewById<Spinner>(R.id.units_selector)

        spinner.onItemSelectedListener = this
        buttonDatePicker.setOnClickListener{
            buttonDatePicker()
        }

        val spinnerAdapter : ArrayAdapter<*> = ArrayAdapter<Any?>(
            this, android.R.layout.simple_spinner_item, units
        )

        spinnerAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinner.adapter = spinnerAdapter

    }

    fun buttonDatePicker() {
        val calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ view, yearSelected, monthSelected, daySelected ->
                    val selectedDate = "$daySelected/${monthSelected + 1}/$yearSelected"
                    textViewDateSelected.text = "Date Selected: $selectedDate"

                    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                    val theDate = simpleDateFormat.parse(selectedDate)
                    Toast.makeText(this, "Hell", Toast.LENGTH_SHORT).show()

                    // The getTime() method returns you the time in milliseconds- to conver the millis to seconds we need to divide by 1000
                    // To get from seconds to minutes we need to divide by 60 - so all together it is 60000 and so on for the conversion

                    // Here we are just getting the time difference in millis so that we can easily convert it to the units we need
                    val selectedTimeInMillis = theDate.time
                    val currentTimeInMillis= simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis())).time

                    val timePassedInMillis = currentTimeInMillis - selectedTimeInMillis

                    when(chosenUnits) {
                        "Days" -> {
                            val timePassedInDays = timePassedInMillis / 86400000
                            textViewAgeDisplay.text = timePassedInDays.toString()
                        }
                        "Hours" -> {
                            val timePassedInHours = timePassedInMillis / 3600000
                            textViewAgeDisplay.text = timePassedInHours.toString()
                        }
                        "Minutes" -> {
                            val timePassedInMinutes = timePassedInMillis / 60000
                            textViewAgeDisplay.text = timePassedInMinutes.toString()
                        }
                        else -> textViewAgeDisplay.text = "Choose"
                    }
                },
                year,
                month,
                day
            ).show()
    }
    fun unitOfMeasureSelected(position: Int) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //Logic to change the unit of measure goes here
        textViewMeasureUnits.text = units[position]
        textViewBirthdayUnits.text = units[position]

        // This will set our global variable for the choice that we can use in the when statement
        chosenUnits = units[position]

        Toast.makeText(applicationContext,
            units[position],
            Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}

