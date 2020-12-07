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
import com.example.slackr.Validators

class SearchDayTime: AppCompatActivity() {

    private var radioGroupTime: RadioGroup? = null
    private var radioButtonTime: RadioButton? = null
    private var timeStr: String? = null
    private var searchButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_group_by_studytime)

        // Get reference
        radioGroupTime = findViewById<View>(R.id.search_radioGroup_type) as RadioGroup
        searchButton = findViewById<View>(R.id.search_search_group) as Button

        // Set on click listener
        searchButton!!.setOnClickListener{

            // Get the ID
            val selectedId = radioGroupTime!!.checkedRadioButtonId

            // If one of the button is selected
            if (selectedId != -1) {

                // Get the string value of the selected radio button
                radioButtonTime = findViewById<View>(selectedId) as RadioButton
                timeStr = radioButtonTime!!.text.toString()

                // Create an intent and start SearchActivity
                val intent = Intent(this@SearchDayTime, SearchActivity::class.java)
                intent.putExtra(SearchEnvironment.SEARCH_TYPE, "studyTime")
                intent.putExtra(SearchEnvironment.SELECTED_BUTTON, timeStr)
                startActivity(intent)

            } else {
                Toast.makeText(applicationContext, "Select One of the Options", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val SEARCH_TYPE = "search_type"
        const val SELECTED_BUTTON = "selected_button"
    }
}