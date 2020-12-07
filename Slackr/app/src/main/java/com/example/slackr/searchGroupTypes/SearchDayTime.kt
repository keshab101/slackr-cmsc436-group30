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

    private lateinit var studyTime: RadioGroup
    private lateinit var searchButton: Button
    private lateinit var timeSelected: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_group_by_studytime)

        studyTime = findViewById(R.id.search_radioGroup_days)
        searchButton = findViewById(R.id.search_group_studyTime)

        val timeSelectedId = studyTime!!.checkedRadioButtonId
        var timeStr = ""
        if (timeSelectedId != 1) {
            timeSelected = findViewById<View>(timeSelectedId) as RadioButton
            timeStr = timeSelected!!.text.toString()
        }

        searchButton.setOnClickListener {

            val validator = Validators()
            if (validator.validText(timeStr)) {
//                val intent = Intent(applicationContext, @SearchActivity::class.java)
//                intent.putExtra(SEARCH_TYPE, "studyTime")
//                intent.putExtra(SELECTED_BUTTON, timeStr)
            } else {
                Toast.makeText(applicationContext,"Please select a time", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val SEARCH_TYPE = "search_type"
        const val SELECTED_BUTTON = "selected_button"
    }
}