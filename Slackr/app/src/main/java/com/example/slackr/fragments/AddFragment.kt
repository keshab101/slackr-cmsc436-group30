package com.example.slackr.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button
import android.widget.EditText
import com.example.slackr.R


class AddFragment : Fragment() {

    private var groupName: EditText? = null
    private var meetingLocation: EditText? = null
    private var description: EditText? = null
    private var subject: AutoCompleteTextView? = null
    private var subjects: Array<String>? = null
    private var adapter: ArrayAdapter<String>? = null
    private var buttonCreateGroup: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add, container, false)

        // Get reference
        groupName = view.findViewById<View>(R.id.group_name) as EditText
        meetingLocation = view.findViewById<View>(R.id.group_location) as EditText
        description = view.findViewById<View>(R.id.group_description) as EditText
        subject = view.findViewById<View>(R.id.group_subject) as AutoCompleteTextView
        buttonCreateGroup = view.findViewById<View>(R.id.create_group_button) as Button

        subjects = resources.getStringArray(R.array.subjects)
        adapter = ArrayAdapter<String>(
            activity!!.applicationContext,
            R.layout.cg_custom_subject_item, R.id.text_view_list_item, subjects!!
        )
        subject!!.setAdapter(adapter)

        // Set on click listener
        buttonCreateGroup!!.setOnClickListener {
            createGroup()
        }

        return view
    }

    // Get called when the button is clicked
    private fun createGroup() {

        // Clear out text fields after creating a group
        groupName!!.text.clear()
        meetingLocation!!.text.clear()
        description!!.text.clear()
        subject!!.text.clear()

    }


    companion object {
        const val TAG = "Slacker-App"
    }
}