package com.example.slackr.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.slackr.MainActivity
import com.example.slackr.R
import com.example.slackr.RegistrationActivity

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.profile, container, false)
        val logoutButton: Button = view.findViewById(R.id.logout)
        logoutButton.setOnClickListener{
            startActivity(Intent(activity, MainActivity::class.java))
        }

        return view
    }

}