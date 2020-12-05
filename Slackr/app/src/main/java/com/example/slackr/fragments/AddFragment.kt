package com.example.slackr.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.slackr.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddFragment : Fragment() {

    private var groupName: EditText? = null
    private var meetingLocation: EditText? = null
    private var description: EditText? = null
    private var subject: AutoCompleteTextView? = null
    private var subjects: Array<String>? = null
    private var adapter: ArrayAdapter<String>? = null
    private var buttonCreateGroup: Button? = null
    private var currentUser: FirebaseUser? = null
    private var databaseRef: DatabaseReference? = null

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

        // Get the information needed to create a group
        currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser!!.uid
        val groupNameStr = groupName!!.text.toString()
        val meetingLocationStr = meetingLocation!!.text.toString()
        val descriptionStr = description!!.text.toString()
        val subjectStr = subject!!.text.toString()
        val groupMemberCount = "1"
        val memberHash = HashMap<String, String>()

        // Get the group ID
        databaseRef = FirebaseDatabase.getInstance().getReference("groups")
        val groupId = databaseRef!!.push().key.toString()

        Log.i(TAG, "groupNameStr: $groupNameStr")
        Log.i(TAG, "groupId: $groupId")
        Log.i(TAG, "groupMemberCount: $groupMemberCount")

        // Add members using a hash map
        memberHash[userId] = userId

        // Create a Group object
        val newGroup = Group(groupId, groupNameStr, groupMemberCount, memberHash,
                             meetingLocationStr, descriptionStr, subjectStr)

        // Store the group into the database
        databaseRef!!.child(groupId).setValue(newGroup).addOnCompleteListener { task ->
            if(task.isSuccessful) {

                // Clear out text fields after creating a group
                groupName!!.text.clear()
                meetingLocation!!.text.clear()
                description!!.text.clear()
                subject!!.text.clear()

                Log.i(TAG, "////////////////////Group was made////////////////////////")

                Toast.makeText(context, "Group Successfully Created", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to create the group", Toast.LENGTH_SHORT).show()
            }
        }
    }


    companion object {
        const val TAG = "Slacker-App"
    }
}