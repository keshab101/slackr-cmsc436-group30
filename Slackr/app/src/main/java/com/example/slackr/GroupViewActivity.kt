package com.example.slackr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class GroupViewActivity : AppCompatActivity() {

    private var groupName: TextView? = null
    private var groupSubject: TextView? = null
    private var groupDescription: TextView? = null
    private var groupLocation: TextView? = null
    private var groupMember: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_view)

        // Get reference
        groupName = findViewById(R.id.group_view_name)
        groupSubject = findViewById(R.id.group_view_subject)
        groupDescription = findViewById(R.id.group_view_description)
        groupLocation = findViewById(R.id.group_view_location)
        groupMember = findViewById(R.id.group_view_group_members)

        // Get necessary information from the intent
        groupName!!.text = intent.getStringExtra("GroupName")
        groupSubject!!.text = intent.getStringExtra("GroupSubject")
        groupDescription!!.text = intent.getStringExtra("GroupDescription")
        groupLocation!!.text = intent.getStringExtra("GroupLocation")
        groupMember!!.text = intent.getStringExtra("GroupMembersCount")

    }

    companion object {
        const val TAG = "Slacker-App"
    }
}