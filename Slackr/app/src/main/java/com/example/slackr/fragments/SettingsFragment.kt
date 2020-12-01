package com.example.slackr.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.slackr.MainActivity
import com.example.slackr.R
import com.example.slackr.RegistrationActivity

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // For menu
        setHasOptionsMenu(true)

        val view =  inflater.inflate(R.layout.profile, container, false)
        val logoutButton: Button = view.findViewById(R.id.logout)
        logoutButton.setOnClickListener{
            startActivity(Intent(activity, MainActivity::class.java))
        }

        return view
    }

    // Creates menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.add(Menu.NONE, MENU_SIGN_OUT, Menu.NONE, R.string.sign_out)
    }

    // Checks if "Sign Out" is clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == MENU_SIGN_OUT) {
            startActivity(Intent(activity, MainActivity::class.java))
            true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    companion object {
        // ID for menu item
        private const val MENU_SIGN_OUT = Menu.FIRST
    }

}