package com.example.slackr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    //Login portion of the app
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var userEmail: EditText? = null
    private var userPassword: EditText? = null
    private var loginBtn: Button? = null
    private var progressBar: ProgressBar? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        userEmail = findViewById(R.id.email)
        userPassword = findViewById(R.id.password)
        loginBtn = findViewById(R.id.login)
        progressBar = findViewById(R.id.progressBar)

        loginBtn!!.setOnClickListener { loginUserAccount() }
    }

    private fun loginUserAccount() {

        progressBar?.visibility = View.VISIBLE

        val email: String = userEmail?.text.toString()
        val password: String = userPassword?.text.toString()


//        if (TextUtils.isEmpty(email)) {
//            Toast.makeText(applicationContext, "Please enter email...", Toast.LENGTH_LONG).show()
//            progressBar!!.visibility = View.GONE
//            return
//        }
//        if (TextUtils.isEmpty(password)) {
//            Toast.makeText(applicationContext, "Please enter password!", Toast.LENGTH_LONG).show()
//            progressBar!!.visibility = View.GONE
//            return
//        }
        val testingEmail = "akeshab301@umd.edu"
        val testingPassword = "cmsc436"
        mAuth!!.signInWithEmailAndPassword(testingEmail, testingPassword)

        //mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar?.visibility = View.GONE
                if(task.isSuccessful){
                    val intent = Intent(this@LoginActivity, HomePage::class.java)
                    intent.putExtra(USER_ID, mAuth!!.uid)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Login failed! Please try again later",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

    }

    companion object {
        const val USER_EMAIL = "student_email"
        const val USER_ID = "student_id"
    }

}