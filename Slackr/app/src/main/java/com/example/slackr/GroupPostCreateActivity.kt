package com.example.slackr

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GroupPostCreateActivity: AppCompatActivity()  {

    private lateinit var postTitle: EditText
    private lateinit var postDesc: EditText
    private lateinit var postButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var currentUser: FirebaseUser
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_post)

        supportActionBar!!.title = "Add New Post"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        //initialize variables from layouts
        postTitle = findViewById(R.id.add_post_title)
        postDesc = findViewById(R.id.add_post_description)
        postButton = findViewById(R.id.create_group_post)
        progressBar = findViewById(R.id.create_post_progressBar)
        //Initialize database variables
        currentUser = FirebaseAuth.getInstance().currentUser!!

        //Get data from intent
        val groupId = intent.getStringExtra("GroupID").toString()

        databaseRef = FirebaseDatabase.getInstance()
            .getReference("groups").child(groupId).child("posts")

        //Add an onclick listener to postButton
        postButton.setOnClickListener {
            createPost()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun createPost() {

        progressBar.visibility = View.VISIBLE

        //Get the information relevant to the post
        val userId = currentUser.uid
        val userName = intent.getStringExtra("userName").toString()
        val userEmail = intent.getStringExtra("userEmail").toString()
        val pTitle = postTitle.text.toString() //maybe add .trim()
        val pDesc = postDesc.text.toString()
        val pTime = System.currentTimeMillis().toString()

        //Create a GroupPost and add it to the database
        val postId = databaseRef.push().key.toString()
        val postHash: HashMap<String, String> = HashMap()
        postHash["userName"] = userName
        postHash["userEmail"] = userEmail
        postHash["userId"] = userId
        postHash["pDesc"] = pDesc
        postHash["pTitle"] = pTitle
        postHash["pTime"] = pTime
        postHash["pId"] = postId

//        val post = GroupPost(postId, pTitle, pDesc, pTime, userId, userName!!, userEmail!!)
        databaseRef.child(postId).setValue(postHash).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                postTitle.text.clear()
                postDesc.text.clear()
                Toast.makeText(applicationContext, "Post Successfully Created", Toast.LENGTH_SHORT).show()
                onBackPressed()
            } else {
                Toast.makeText(applicationContext, "Failed to create the post", Toast.LENGTH_SHORT).show()
            }
            progressBar.visibility = View.GONE
        }
    }


}