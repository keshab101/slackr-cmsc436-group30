package com.example.slackr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GroupViewActivity : AppCompatActivity() {

    private var groupName: TextView? = null
    private var groupSubject: TextView? = null
    private var groupDescription: TextView? = null
    private var groupLocation: TextView? = null
    private var groupMember: TextView? = null
    private var members: TextView? = null
    private var currentUser: FirebaseUser? = null
    private var membersHash: HashMap<String, String> = HashMap()
    private var membersNameHash: HashMap<String, String> = HashMap()
    private var membersCount: String? = null
    private var memberNameString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_view)

        // Get reference
        groupName = findViewById(R.id.group_view_name)
        groupSubject = findViewById(R.id.group_view_subject)
        groupDescription = findViewById(R.id.group_view_description)
        groupLocation = findViewById(R.id.group_view_location)
        groupMember = findViewById(R.id.group_view_group_members)
        members = findViewById(R.id.group_view_members)

        // Get necessary information from the intent
        groupName!!.text = intent.getStringExtra("GroupName")
        groupSubject!!.text = intent.getStringExtra("GroupSubject")
        groupDescription!!.text = intent.getStringExtra("GroupDescription")
        groupLocation!!.text = intent.getStringExtra("GroupLocation")
        groupMember!!.text = intent.getStringExtra("GroupMembersCount")
        //members!!.text = intent.getStringExtra("Members")
        membersCount = intent.getStringExtra("GroupMembersCount").toString()

        // Display the names of the users in this group
        val groupId = intent.getStringExtra("GroupId").toString()
        val databaseRefName = FirebaseDatabase.getInstance().getReference("groups").child(groupId)
        val databaseRefUser = FirebaseDatabase.getInstance().getReference("users")

        databaseRefName.child("members").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                // Get the members HashMap
                membersNameHash = snapshot.value as HashMap<String, String>

                // Get names of the users in this group
                databaseRefUser.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (ds in snapshot.children) {

                            if (membersNameHash.contains(ds.key.toString())) {
                                memberNameString += ds.child("userName").value.toString()
                                memberNameString += "\n"
                            }
                        }
                        memberNameString = memberNameString.trim()
                        members!!.text = memberNameString
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        // Set up the action bar to display group name
        supportActionBar!!.title = "${groupName!!.text} Info"

    }

    //If the user clicks on back button at top, go back to home page
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menu.add(Menu.NONE, MENU_LEAVE, Menu.NONE, "Leave This Group")
        return true
    }

    //When the menu options is clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == MENU_LEAVE) {

            // Get the id of the current user
            currentUser = FirebaseAuth.getInstance().currentUser
            val userId = currentUser!!.uid

            val groupId = intent.getStringExtra("GroupId").toString()
            val groupName = intent.getStringExtra("GroupName").toString()
            val databaseRef = FirebaseDatabase.getInstance().getReference("groups").child(groupId)
            val databaseGroup = FirebaseDatabase.getInstance().getReference("groups")
            val databaseHabits = FirebaseDatabase.getInstance().getReference("studyHabits")
            var membersCountInt = Integer.parseInt(membersCount!!)

            databaseRef.child("members").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    // Get the members HashMap
                    membersHash = snapshot.value as HashMap<String, String>

                    // Remove the id of the current user from the members HashMap and update the database
                    membersHash.remove(userId)
                    databaseRef.child("members").setValue(membersHash)

                    // Decrement membersCount and update the database
                    membersCountInt--
                    databaseRef.child("groupMembers").setValue(membersCountInt.toString())

                    // Remove the group and study habit if there is no member
                    if (membersCountInt == 0) {
                        databaseGroup.child(groupId).removeValue()
                        databaseHabits.child(groupId).removeValue()
                    }

                    Toast.makeText(applicationContext, "You Left $groupName", Toast.LENGTH_SHORT).show()

                    // Go back to HomePage
                    onBackPressed()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
                true

        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val TAG = "Slacker-App"
        private const val MENU_LEAVE = Menu.FIRST
    }
}