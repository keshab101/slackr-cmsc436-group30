package com.example.slackr.searchGroupTypes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.slackr.R

class SearchWeekly: AppCompatActivity() {

    private var radioGroupDay: RadioGroup? = null
    private var radioButtonDay: RadioButton? = null
    private var dayStr: String? = null
    private var searchButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_group_by_week)

        // Get reference
        radioGroupDay = findViewById<View>(R.id.search_radioGroup_weekdays) as RadioGroup
        searchButton = findViewById<View>(R.id.search_group_week) as Button

        // Set on click listener
        searchButton!!.setOnClickListener{

            // Get the ID
            val selectedId = radioGroupDay!!.checkedRadioButtonId

            // If one of the button is selected
            if (selectedId != -1) {

                // Get the string value of the selected radio button
                radioButtonDay = findViewById<View>(selectedId) as RadioButton
                dayStr = radioButtonDay!!.text.toString()

                // Create an intent and start SearchActivity
                val intent = Intent(this@SearchWeekly, SearchActivity::class.java)
                intent.putExtra(SEARCH_TYPE, "studyDay")
                intent.putExtra(SELECTED_BUTTON, dayStr)
                startActivity(intent)

            } else {
                Toast.makeText(applicationContext, "Select One of the Options", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val TAG = "Slacker-App"
        const val SEARCH_TYPE = "search_type"
        const val SELECTED_BUTTON = "selected_button"
    }
}