package com.example.slackr.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.slackr.EditProfile
import com.example.slackr.MainActivity
import com.example.slackr.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class SettingsFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null
    private var databaseRef: DatabaseReference? = null
    private var userNameTop: TextView? = null
    private var userNameBottom: TextView? = null
    private var userEmail: TextView? = null
    private var userSocial: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        userNameTop = view.findViewById<View>(R.id.tv_name) as TextView
        userNameBottom = view.findViewById<View>(R.id.tv_name_bottom) as TextView
        userEmail = view.findViewById<View>(R.id.tv_email) as TextView
        userSocial = view.findViewById<View>(R.id.tv_sns_account) as TextView

        currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser!!.uid
        databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId)

        databaseRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                userNameTop!!.text = snapshot.child("userName").value.toString()
                userNameBottom!!.text = snapshot.child("userName").value.toString()
                userEmail!!.text = snapshot.child("email").value.toString()

                if (snapshot.hasChild("social")) {
                    userSocial!!.text = snapshot.child("social").value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        // For menu
        setHasOptionsMenu(true)
        firebaseAuth = FirebaseAuth.getInstance()

        return view
    }
    // Creates menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.add(Menu.NONE, MENU_EDIT_PROFILE, Menu.NONE, R.string.edit_profile)
        menu.add(Menu.NONE, MENU_SIGN_OUT, Menu.NONE, R.string.sign_out)

    }

    // Checks if "Sign Out" is clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            MENU_EDIT_PROFILE -> {
                startActivity(Intent(context, EditProfile::class.java))
                true
            }
            MENU_SIGN_OUT -> {
                firebaseAuth.signOut()
                val intent = Intent(context, MainActivity::class.java)

                // Looked up this
                // https://medium.com/swlh/truly-understand-tasks-and-back-stack-intent-flags-of-activity-2a137c401eca
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    companion object {
        // ID for menu item
        private const val MENU_SIGN_OUT = Menu.FIRST + 1
        private const val MENU_EDIT_PROFILE = Menu.FIRST
        const val TAG = "Slacker-App"
    }

}