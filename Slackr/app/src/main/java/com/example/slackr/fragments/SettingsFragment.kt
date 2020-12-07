package com.example.slackr.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.slackr.EditProfile
import com.example.slackr.MainActivity
import com.example.slackr.R
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // For menu
        setHasOptionsMenu(true)
        firebaseAuth = FirebaseAuth.getInstance()

        return inflater.inflate(R.layout.fragment_settings, container, false)
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
    }

}