package com.example.slackr

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    private lateinit var groupId: String
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
        groupId = intent.getStringExtra("GroupId").toString()
        groupName = intent.getStringExtra("GroupName").toString()
        currentUser = FirebaseAuth.getInstance().currentUser!!
        databaseRef = FirebaseDatabase.getInstance().getReference("groups").child(groupId)

        //Set up action bar
        supportActionBar!!.title = groupName
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        //Add event listener to reference to posts
        databaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var post: GroupPost?
                for (ds in snapshot.child("posts").children) {
                    post = ds.getValue(GroupPost::class.java)
                    //Log.i("Slacker-App", " postTime: ${ds.child(ptime)}")
                    groupPosts.add(post!!)
                }
                mRecyclerView.adapter = GroupPostAdapter(groupPosts, R.layout.single_group_post)
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "There was an error while accessing the group", Toast.LENGTH_LONG).show()
            }
        })
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
                val usersDB = FirebaseDatabase.getInstance().getReference("users").child(currentUser.uid)
                Log.d(TAG, "usersDB: $usersDB")
                var userEmail: String? = null
                var userName: String? = null

                usersDB.addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        userEmail = snapshot.child("email").value.toString()
                        userName = snapshot.child("userName").value.toString()
//                        Log.i(TAG, "userName: $userName")
//                        Log.i(TAG, "Email: $userEmail")
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
                Log.i(TAG, "userName: $userName")
                Log.i(TAG, "Email: $userEmail")

                //Create an intent and launch it with some post information
                val intent = Intent(applicationContext, GroupPostCreateActivity::class.java)
                intent.putExtra("GroupName", groupName)
                intent.putExtra("GroupID", groupId)
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

    companion object {
        const val TAG = "Slacker-App"
    }

}