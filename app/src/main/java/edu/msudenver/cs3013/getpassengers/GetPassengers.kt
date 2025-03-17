package edu.msudenver.cs3013.getpassengers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GetPassengersActivity : AppCompatActivity() {

    data class Passenger(val fName: String, val lName: String, val phone: String) {
        override fun toString(): String {
            return "$fName $lName - $phone"
        }
    }

    private val passList: MutableList<Passenger> = mutableListOf()

    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var addPassengerButton: Button
    private lateinit var returnListButton: Button
    private lateinit var accumulatingPassengerListText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_passengers)

        firstNameEditText = findViewById(R.id.FirstName)
        lastNameEditText = findViewById(R.id.LastName)
        phoneNumberEditText = findViewById(R.id.PhoneNumber)
        addPassengerButton = findViewById(R.id.btnAddPassenger)
        returnListButton = findViewById(R.id.btnReturnList)
        accumulatingPassengerListText = findViewById(R.id.tvPassengerList)

        addPassengerButton.setOnClickListener { enterPassenger() }
        returnListButton.setOnClickListener { sendPassengerListBack() }
    }

    private fun enterPassenger() {
        val firstName = firstNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val phoneNumber = phoneNumberEditText.text.toString().trim()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && phoneNumber.isNotEmpty()) {
            val newPassenger = Passenger(firstName, lastName, phoneNumber)
            passList.add(newPassenger)

            accumulatingPassengerListText.append("\n${newPassenger}")

            firstNameEditText.text.clear()
            lastNameEditText.text.clear()
            phoneNumberEditText.text.clear()
        }
    }

    private fun sendPassengerListBack() {
        if (passList.isEmpty()) {
            setResult(Activity.RESULT_CANCELED) // Don't return empty data
        } else {
            val resultIntent = Intent()
            resultIntent.putExtra("COUNT", passList.size)

            for (i in passList.indices) {
                resultIntent.putExtra("PASS${i + 1}", passList[i].toString())
            }

            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }
}
