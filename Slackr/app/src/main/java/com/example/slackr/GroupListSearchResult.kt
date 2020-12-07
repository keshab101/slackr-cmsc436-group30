package com.example.slackr

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.slackr.fragments.SearchFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GroupListSearchResult(private val context: Activity, private var groups: List<Group>) : ArrayAdapter<Group>(context,
    R.layout.search_group_results_listitem, groups) {

    private var groupName: TextView? = null
    private var groupMembers: TextView? = null
    private var group: Group? = null
    private var name: String? = null
    private var id: String? = null
    private var description: String? = null
    private var location: String? = null
    private var subject: String? = null
    private var membersCount: String? = null
    private var members: HashMap<String, String> = HashMap()
    private var currentUser: FirebaseUser? = null
    private var userId: String? = null
    private var databaseRef: DatabaseReference? = null

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val groupListItem = inflater.inflate(R.layout.search_group_results_listitem, null, true)

        // Get reference
        groupName = groupListItem.findViewById<View>(R.id.result_group_name) as TextView
        groupMembers = groupListItem.findViewById<View>(R.id.result_group_members) as TextView

        group = groups[position]
        name = group!!.groupName
        id = group!!.groupId
        description = group!!.groupDescription
        location = group!!.groupLocation
        subject = group!!.groupSubject
        membersCount = group!!.groupMembers
        members = group!!.members
        groupName!!.text = name
        groupMembers!!.text = ("$membersCount members")
        currentUser = FirebaseAuth.getInstance().currentUser
        userId = currentUser!!.uid
        databaseRef = FirebaseDatabase.getInstance().getReference("groups").child(id!!)
        var membersCountInt = Integer.parseInt(membersCount)

        val joinButton = groupListItem.findViewById<View>(R.id.result_group_join_button) as Button
        joinButton.setOnClickListener {

            // Add the id of the current user to members HashMap and update the database
            members[userId!!] = userId!!
            databaseRef!!.child("members").setValue(members)

            // Increment membersCount and update the database
            membersCountInt++
            databaseRef!!.child("groupMembers").setValue(membersCountInt.toString())

            Toast.makeText(context, "You Joined to $groupName", Toast.LENGTH_SHORT).show()

            //Go back to SearchFragment
            val intent = Intent(context, SearchFragment::class.java)
            context.startActivity(intent)
        }

        val viewButton = groupListItem.findViewById<View>(R.id.result_group_view_button) as Button
        viewButton.setOnClickListener {

            val resultViewIntent = Intent(context, GroupResultViewActivity::class.java)
            resultViewIntent.putExtra("GroupName", name)
            resultViewIntent.putExtra("GroupId", id)
            resultViewIntent.putExtra("GroupDescription", description)
            resultViewIntent.putExtra("GroupLocation", location)
            resultViewIntent.putExtra("GroupSubject", subject)
            resultViewIntent.putExtra("GroupMembersCount", membersCount)

            context.startActivity(resultViewIntent)
        }

        return groupListItem
    }

    override fun getItem(position: Int): Group {
        return groups[position]
    }

    companion object {
        const val TAG = "Slacker-App"
    }

}