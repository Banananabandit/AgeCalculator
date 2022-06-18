package android.banananabandit.agecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val units = arrayOf<String?>("Months", "Weeks", "Days", "Hours", "Minutes")
    lateinit var measureUnits : TextView
    lateinit var birthdayUnits : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        measureUnits = findViewById(R.id.age_measure_unit)
        birthdayUnits = findViewById(R.id.birthday_measure_unit)

        val spinner = findViewById<Spinner>(R.id.units_selector)
        spinner.onItemSelectedListener = this

        val spinnerAdapter : ArrayAdapter<*> = ArrayAdapter<Any?>(
            this, android.R.layout.simple_spinner_item, units
        )

        spinnerAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinner.adapter = spinnerAdapter

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //Logic to change the unit of measure goes here
        measureUnits.text = units[position]
        birthdayUnits.text = units[position]

        Toast.makeText(applicationContext,
            units[position],
            Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}