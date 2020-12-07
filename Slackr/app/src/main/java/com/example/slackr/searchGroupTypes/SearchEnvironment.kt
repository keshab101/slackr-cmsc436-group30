package com.example.slackr.searchGroupTypes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.slackr.R

class SearchEnvironment: AppCompatActivity() {

    private var radioGroupType: RadioGroup? = null
    private var radioButtonType: RadioButton? = null
    private var typeStr: String? = null
    private var searchButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_group_by_studytype)

        // Get reference
        radioGroupType = findViewById<View>(R.id.search_radioGroup_type) as RadioGroup
        searchButton = findViewById<View>(R.id.search_search_group) as Button

        // Set on click listener
        searchButton!!.setOnClickListener{

            // Get the ID
            val selectedId = radioGroupType!!.checkedRadioButtonId

            // If one of the button is selected
            if (selectedId != -1) {

                // Get the string value of the selected radio button
                radioButtonType = findViewById<View>(selectedId) as RadioButton
                typeStr = radioButtonType!!.text.toString()

                // Create an intent and start SearchActivity
                val intent = Intent(this@SearchEnvironment, SearchActivity::class.java)
                intent.putExtra(SEARCH_TYPE, "studyType")
                intent.putExtra(SELECTED_BUTTON, typeStr)
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