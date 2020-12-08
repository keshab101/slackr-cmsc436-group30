package com.example.slackr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/** Some of the code in this class was derived from Lab-07- Firebase*/
class RegistrationActivity : AppCompatActivity() {

    private var userName: EditText? = null
    private var emailTV: EditText? = null
    private var passwordTV: EditText? = null
    private var regBtn: Button? = null
    private var progressBar: ProgressBar? = null
    private var validator = Validators()
    private var mAuth: FirebaseAuth? = null
    private lateinit var firebaseRef: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        mAuth = FirebaseAuth.getInstance()
        firebaseRef = FirebaseDatabase.getInstance()
        databaseRef = firebaseRef.getReference("users")

        userName = findViewById(R.id.name)
        emailTV = findViewById(R.id.email)
        passwordTV = findViewById(R.id.password)
        regBtn = findViewById(R.id.register)
        progressBar = findViewById(R.id.progressBar)

        regBtn!!.setOnClickListener { registerNewUser() }

    }
    private fun registerNewUser() {
        progressBar!!.visibility = View.VISIBLE

        val email: String = emailTV!!.text.toString()
        val password: String = passwordTV!!.text.toString()
        val name: String = userName!!.text.toString()

        //Write code to validate user
        if (!validator.validUserName(name)) {
            Toast.makeText(applicationContext, "Please enter a name with at least 3 characters", Toast.LENGTH_LONG).show()
            progressBar!!.visibility = View.GONE
            return
        }
        if (!validator.validEmail(email)) {
            Toast.makeText(applicationContext, "Please enter a school email", Toast.LENGTH_LONG).show()
            progressBar!!.visibility = View.GONE
            return
        }
        if (!validator.validPassword(password)) {
            Toast.makeText(applicationContext, "Please enter a valid password!", Toast.LENGTH_LONG).show()
            progressBar!!.visibility = View.GONE
            return
        }

        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Toast.makeText(applicationContext, "User Successfully Created", Toast.LENGTH_LONG).show()
                    progressBar!!.visibility = View.GONE

                    val userID = mAuth!!.currentUser?.uid.toString()
                    //Create a Hashmap containing user's name and email
                    val userHash: HashMap<String, String> = HashMap()
                    userHash["userName"] = name
                    userHash["email"] = email

                    //Storing the user's information in database
                    databaseRef.child(userID).setValue(userHash)

                    val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                    startActivity(intent)


                } else {
                    Toast.makeText(applicationContext, "Registration failed! Please try again", Toast.LENGTH_LONG).show()
                    progressBar!!.visibility = View.GONE
                }
            }
    }
}