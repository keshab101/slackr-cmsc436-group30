package com.example.slackr.searchGroupTypes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.slackr.R

class SearchLearningType: AppCompatActivity() {

    private var radioGroupMethod: RadioGroup? = null
    private var radioButtonMethod: RadioButton? = null
    private var methodStr: String? = null
    private var searchButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_group_by_learning_type)

        // Get reference
        radioGroupMethod = findViewById<View>(R.id.search_radioGroup_method) as RadioGroup
        searchButton = findViewById<View>(R.id.search_group_learning) as Button

        // Set on click listener
        searchButton!!.setOnClickListener{

            // Get the ID
            val selectedId = radioGroupMethod!!.checkedRadioButtonId

            // If one of the button is selected
            if (selectedId != -1) {

                // Get the string value of the selected radio button
                radioButtonMethod = findViewById<View>(selectedId) as RadioButton
                methodStr = radioButtonMethod!!.text.toString()

                // Create an intent and start SearchActivity
                val intent = Intent(this@SearchLearningType, SearchActivity::class.java)
                intent.putExtra(SEARCH_TYPE, "studyMethod")
                intent.putExtra(SELECTED_BUTTON, methodStr)
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