package com.example.slackr

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class GroupDetailActivity : AppCompatActivity() {

    private var groupName: TextView? = null
    private var groupMembers: TextView? = null
    private var groupID: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groupdetail)

        groupName = findViewById(R.id.group_name)
        groupMembers = findViewById(R.id.group_members)
        groupID = findViewById(R.id.group_id)

        groupName!!.text = intent.getStringExtra("GroupName")
        groupMembers!!.text = intent.getStringExtra("GroupMembers")
        groupID!!.text = intent.getStringExtra("GroupID")

    }
}