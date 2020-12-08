package com.example.slackr.groupDisplay

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.slackr.R

class GroupResultViewActivity : AppCompatActivity() {
    private var groupName: TextView? = null
    private var groupSubject: TextView? = null
    private var groupDescription: TextView? = null
    private var groupLocation: TextView? = null
    private var groupMember: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_result_view)

        // Get reference
        groupName = findViewById(R.id.group_result_view_name)
        groupSubject = findViewById(R.id.group_result_view_subject)
        groupDescription = findViewById(R.id.group_result_view_description)
        groupLocation = findViewById(R.id.group_result_view_location)
        groupMember = findViewById(R.id.group_result_view_group_members)

        // Get necessary information from the intent
        groupName!!.text = intent.getStringExtra("GroupName")
        groupSubject!!.text = intent.getStringExtra("GroupSubject")
        groupDescription!!.text = intent.getStringExtra("GroupDescription")
        groupLocation!!.text = intent.getStringExtra("GroupLocation")
        groupMember!!.text = intent.getStringExtra("GroupMembersCount")

        // Set up the action bar to display group name
        supportActionBar!!.title = "${groupName!!.text} Info"

    }

    // We used Log to debug our code
    companion object {
        const val TAG = "Slacker-App"
    }
}