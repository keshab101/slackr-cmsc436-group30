package com.example.slackr.searchGroupTypes

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.example.slackr.R

class SearchSubject: AppCompatActivity() {

    private var subjects: Array<String>? = null
    private var adapter: ArrayAdapter<String>? = null
    private var subject: AutoCompleteTextView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_group_by_subject)

        subject = findViewById<View>(R.id.group_subject) as AutoCompleteTextView
        subjects = resources.getStringArray(R.array.subjects)
        adapter = ArrayAdapter<String>(
            this,
            R.layout.cg_custom_subject_item, R.id.text_view_list_item, subjects!!
        )
        subject!!.setAdapter(adapter)
    }
}