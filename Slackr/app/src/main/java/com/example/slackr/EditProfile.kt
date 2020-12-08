package com.example.slackr

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class EditProfile: AppCompatActivity() {
    
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var socialMedia: EditText
    private lateinit var submitButton: Button
    private lateinit var user: FirebaseUser
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var usersDB: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.edit_profile)

        // Initialize variables
        name = findViewById(R.id.edit_userName)
        email = findViewById(R.id.edit_email)
        password = findViewById(R.id.edit_userPassword)
        socialMedia = findViewById(R.id.edit_socialMediaAccount)
        submitButton = findViewById(R.id.settings_submit)

        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser!!
        usersDB = FirebaseDatabase.getInstance().getReference("users")

        // Set on click listener for submit button
        submitButton.setOnClickListener {
            updateUserInfo()
        }


    }
    private fun updateUserInfo() {

        val validator = Validators()
        val nameText = name.text.toString()
        val emailText = email.text.toString()
        val passwordText = password.text.toString()
        val socialMediaText = socialMedia.text.toString()

        if (nameText != "") {
            if (validator.validUserName(nameText)) {
                //Change the username
                usersDB.child(user.uid).child("userName").setValue(nameText)
                name.text.clear()
                Toast.makeText(applicationContext,"Username successfully changed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext,"Please enter a valid name", Toast.LENGTH_SHORT).show()
            }
        }

        if (emailText != "") {
            if (validator.validEmail(emailText)) {
                //Change the email
                usersDB.child(user.uid).child("email").setValue(emailText)
                user.updateEmail(emailText).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        email.text.clear()
                        Toast.makeText(applicationContext,"email successfully changed", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext,"There was an error", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(applicationContext,"Please enter a valid email", Toast.LENGTH_SHORT).show()
            }
        }

        if (passwordText != "") {
            if (validator.validPassword(passwordText)) {
                //Change the password
                user.updatePassword(passwordText).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        password.text.clear()
                        Toast.makeText(applicationContext,"Password successfully changed", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext,"There was an error", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(applicationContext,"Please enter a valid password", Toast.LENGTH_SHORT).show()
            }
        }

        if (socialMediaText != "") {
            if (validator.validText(socialMediaText)) {
                //Change social media textview
                usersDB.child(user.uid).child("social").setValue(socialMediaText)
                socialMedia.text.clear()
                Toast.makeText(applicationContext,"Social media successfully changed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext,"Please enter a valid Social media account", Toast.LENGTH_SHORT).show()
            }
        }

    }
}