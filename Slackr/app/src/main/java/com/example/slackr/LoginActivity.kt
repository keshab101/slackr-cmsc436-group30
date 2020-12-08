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


/** Some of the code in this class was derived from Lab-07- Firebase*/
class LoginActivity : AppCompatActivity() {

    //Login portion of the app
    private var usersDB: DatabaseReference? = null
    private var firebaseDatabase: FirebaseDatabase? = null
    private var userEmail: EditText? = null
    private var userPassword: EditText? = null
    private var loginBtn: Button? = null
    private var progressBar: ProgressBar? = null
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseDatabase = FirebaseDatabase.getInstance()
        usersDB= firebaseDatabase!!.reference.child("users")
        firebaseAuth = FirebaseAuth.getInstance()

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


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Please enter the email.", Toast.LENGTH_LONG).show()
            progressBar!!.visibility = View.GONE
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Please enter the password!", Toast.LENGTH_LONG).show()
            progressBar!!.visibility = View.GONE
            return
        }

        firebaseAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar?.visibility = View.GONE
                if(task.isSuccessful){
                    val intent = Intent(this@LoginActivity, HomePage::class.java)
                    intent.putExtra(USER_ID, firebaseAuth!!.uid)
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