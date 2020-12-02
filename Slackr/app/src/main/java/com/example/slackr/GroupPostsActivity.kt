package com.example.slackr

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slackr.fragments.Group
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class GroupPostsActivity : AppCompatActivity() {

    private lateinit var groupPosts: MutableList<GroupPost>
    private lateinit var groupID: String
    private lateinit var groupName: String
    private lateinit var databaseRef: DatabaseReference
    private lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_posts)

        //Set up RecyclerView
        val mRecyclerView = findViewById<RecyclerView>(R.id.group_posts_list)

        //Set the layout manager
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        (mRecyclerView.layoutManager as LinearLayoutManager).stackFromEnd = true
        (mRecyclerView.layoutManager as LinearLayoutManager).reverseLayout = true

        //Set up the adapter
        groupPosts = ArrayList()
        groupID = intent.getStringExtra("GroupID").toString()
        groupName = intent.getStringExtra("GroupName").toString()
        currentUser = FirebaseAuth.getInstance().currentUser!!
        databaseRef = FirebaseDatabase.getInstance().getReference("groups")

        //Set up action bar
        supportActionBar!!.title = groupName
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        //Add event listener to database
        databaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                groupPosts.clear()
                var post: GroupPost? = null
                for (ds in snapshot.child(groupID).child("posts").children) {
                    post = ds.getValue(GroupPost::class.java)
                    groupPosts.add(post!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "There was an error while accessing the group", Toast.LENGTH_LONG).show()
            }
        })
        mRecyclerView.adapter = GroupPostAdapter(groupPosts, R.layout.single_group_post)
    }

    //When the menu options is clicked
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "Add a new Post")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            Menu.FIRST -> {

                //Get users database to extract user's email and username
                val usersDB = FirebaseDatabase.getInstance().getReference("users")
                var userEmail: String? = null
                var userName: String? = null

                usersDB.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (ds in snapshot.child(currentUser.uid).children) {
                            userEmail = ds.child("email").value as String?
                            userName = ds.child("userName").value as String?
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })

                //Create an intent and launch it with some post information
                val intent = Intent(applicationContext, GroupPostCreateActivity::class.java)
                intent.putExtra("GroupName", groupName)
                intent.putExtra("GroupID", groupID)
                intent.putExtra("userEmail", userEmail)
                intent.putExtra("userName", userName)
                startActivity(intent)
                true
            }
            else -> false
        }
    }

    //If the user clicks on back button at top, go back to home page
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}