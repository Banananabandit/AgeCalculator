package android.banananabandit.agecalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val units = arrayOf<String?>("Days", "Hours", "Minutes")
    private lateinit var textViewMeasureUnits : TextView
    private lateinit var textViewBirthdayUnits : TextView
    private lateinit var textViewDateSelected : TextView
    private lateinit var textViewAgeDisplay : TextView

    private var timePassed : Long? = 0
    private var chosenUnits : String? = null
    private var timePassedInMillis : Long? = 0
    private var selectedDate : String? = null

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

    private fun buttonDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{ view, yearSelected, monthSelected, daySelected ->
                selectedDate = "$daySelected/${monthSelected + 1}/$yearSelected"
                textViewDateSelected.text = "Date Selected: $selectedDate"

                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = simpleDateFormat.parse(selectedDate)

                // Use the let operator to make sure that the text which is within doesn't execute
                theDate?.let {
                    val selectedTimeInMillis = theDate.time
                    val currentTimeInMillis = simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis())).time
                    currentTimeInMillis.let {
                        timePassedInMillis = currentTimeInMillis - selectedTimeInMillis
                        unitOfMeasureSelected()
                    }
                }
            },
            year,
            month,
            day
        )
        datePicker.datePicker.maxDate = System.currentTimeMillis() - 86400
        datePicker.show()
    }
    private fun unitOfMeasureSelected() {
        when(chosenUnits) {
            "Days" -> {
                timePassed = timePassedInMillis!! / 86400000
                textViewAgeDisplay.text = timePassed.toString()
            }
            "Hours" -> {
                timePassed = timePassedInMillis!! / 3600000
                textViewAgeDisplay.text = timePassed.toString()
            }
            "Minutes" -> {
                timePassed = timePassedInMillis!! / 60000
                textViewAgeDisplay.text = timePassed.toString()
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        textViewMeasureUnits.text = units[position]
        textViewBirthdayUnits.text = units[position]

        chosenUnits = units[position]
        unitOfMeasureSelected()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}

