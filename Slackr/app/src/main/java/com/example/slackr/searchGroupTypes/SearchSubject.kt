package com.example.slackr.searchGroupTypes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.slackr.R
import com.example.slackr.Validators

class SearchSubject: AppCompatActivity() {

    private var subjects: Array<String>? = null
    private var adapter: ArrayAdapter<String>? = null
    private var subject: AutoCompleteTextView? = null
    private var searchButton: Button? = null
    private var subjectStr: String? = null
    private val validator = Validators()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_group_by_subject)

        // Get reference
        searchButton = findViewById<View>(R.id.search_group_bySubject) as Button
        subject = findViewById<View>(R.id.group_subject) as AutoCompleteTextView

        subjects = resources.getStringArray(R.array.subjects)
        adapter = ArrayAdapter<String>(
            this,
            R.layout.cg_custom_subject_item, R.id.text_view_list_item, subjects!!
        )
        subject!!.setAdapter(adapter)

        // Set on click listener
        searchButton!!.setOnClickListener {

            subjectStr = subject!!.text.toString()

            if (validator.validText(subjectStr)) {

                // Create an intent and start SearchActivity
                val intent = Intent(this@SearchSubject, SearchActivity::class.java)
                intent.putExtra(SEARCH_TYPE, "studySubject")
                intent.putExtra(SELECTED_BUTTON, subjectStr)
                startActivity(intent)

            } else {
                Toast.makeText(applicationContext, "Select a Subject", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val TAG = "Slacker-App"
        const val SEARCH_TYPE = "search_type"
        const val SELECTED_BUTTON = "selected_button"
    }
}