package edu.msudenver.cs3013.getpassengers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private lateinit var listText: TextView
    private lateinit var getPassengerListButton: Button

    // Register for activity result to get data from GetPassengers
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val count = data?.getIntExtra("COUNT", 0) ?: 0 // Use safe integer retrieval
            val passengerList = StringBuilder()

            for (i in 1..count) {
                val passenger = data?.getStringExtra("PASS$i") ?: ""
                passengerList.append("$passenger\n")
            }

            listText.text = if (count > 0) passengerList.toString() else "No passengers added."
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listText = findViewById(R.id.tvPassengerList)
        getPassengerListButton = findViewById(R.id.btnGetPassengers)

        getPassengerListButton.setOnClickListener {
            openGetPassengers()
        }
    }

    private fun openGetPassengers() {
        val intent = Intent(this, GetPassengers::class.java)
        startForResult.launch(intent) // Start GetPassengersActivity
    }
}
