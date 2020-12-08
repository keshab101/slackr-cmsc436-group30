package com.example.slackr.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.slackr.*
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
    private var databaseRefGroup: DatabaseReference? = null
    private var databaseRefHabit: DatabaseReference? = null
    private var radioGroupDay: RadioGroup? = null
    private var radioGroupTime: RadioGroup? = null
    private var radioGroupType: RadioGroup? = null
    private var radioGroupMethod: RadioGroup? = null


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
        radioGroupDay = view.findViewById<View>(R.id.radioGroup_days) as RadioGroup
        radioGroupTime = view.findViewById<View>(R.id.radioGroup_time) as RadioGroup
        radioGroupType = view.findViewById<View>(R.id.radioGroup_type) as RadioGroup
        radioGroupMethod = view.findViewById<View>(R.id.radioGroup_method) as RadioGroup

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
        val validator = Validators()

        // Get selected radio buttons' ids
        val daySelectedId = radioGroupDay!!.checkedRadioButtonId
        val timeSelectedId = radioGroupTime!!.checkedRadioButtonId
        val typeSelectedId = radioGroupType!!.checkedRadioButtonId
        val methodSelectedId = radioGroupMethod!!.checkedRadioButtonId

        var daySelected: RadioButton
        var timeSelected: RadioButton
        var typeSelected: RadioButton
        var methodSelected: RadioButton

        var dayStr = ""
        var timeStr = ""
        var typeStr = ""
        var methodStr = ""

        // If one of the options for each habit is selected
        if ((daySelectedId != -1) && (timeSelectedId != -1) && (typeSelectedId != -1) && (methodSelectedId != -1)) {

            // Get selected radio buttons
            daySelected = view!!.findViewById<View>(daySelectedId) as RadioButton
            timeSelected = view!!.findViewById<View>(timeSelectedId) as RadioButton
            typeSelected = view!!.findViewById<View>(typeSelectedId) as RadioButton
            methodSelected = view!!.findViewById<View>(methodSelectedId) as RadioButton

            // Get string values from selected buttons
            dayStr = daySelected.text.toString()
            timeStr = timeSelected.text.toString()
            typeStr = typeSelected.text.toString()
            methodStr = methodSelected.text.toString()
        }

        // Create a StudyHabit object
        val newStudyHabit = StudyHabit(dayStr, timeStr, typeStr, methodStr, subjectStr)
        databaseRefHabit = FirebaseDatabase.getInstance().getReference("studyHabits")


        // Get the group ID
        databaseRefGroup = FirebaseDatabase.getInstance().getReference("groups")
        val groupId = databaseRefGroup!!.push().key.toString()

        // Add members using a hash map
        memberHash[userId] = userId

        // Create a Group object
        val newGroup = Group(groupId, groupNameStr, groupMemberCount, memberHash,
            meetingLocationStr, descriptionStr, subjectStr)

        if (validator.validText(groupNameStr) && validator.validText(descriptionStr)
            && validator.validText(subjectStr) && validator.validText(dayStr)
            && validator.validText(timeStr) && validator.validText(typeStr) &&
            validator.validText(methodStr)) {

            // Store the group into the database
            databaseRefGroup!!.child(groupId).setValue(newGroup).addOnCompleteListener { taskGroup ->
                //if (taskGroup.isSuccessful) {

                    // Store the study habit into the database
                    databaseRefHabit!!.child(groupId).setValue(newStudyHabit).addOnCompleteListener { taskHabit ->
                            if (taskGroup.isSuccessful && taskHabit.isSuccessful) {

                                // Clear out text fields after creating a group
                               // groupName!!.text.clear()
                               // meetingLocation!!.text.clear()
                               // description!!.text.clear()
                               // subject!!.text.clear()

                                // Reset radio groups
                               // radioGroupDay!!.clearCheck()
                               // radioGroupTime!!.clearCheck()
                               // radioGroupType!!.clearCheck()
                               // radioGroupMethod!!.clearCheck()

                                val intent = Intent(context, HomePage::class.java)
                                startActivity(intent)
                                Toast.makeText(context, "Group Successfully Created", Toast.LENGTH_SHORT).show()

                            } else {
                                Toast.makeText(context, "Failed to Create the Group", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        } else {
            Toast.makeText(context, "Failed to Create the Group", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val TAG = "Slacker-App"
    }
}