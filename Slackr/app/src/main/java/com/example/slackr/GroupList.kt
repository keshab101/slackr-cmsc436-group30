package com.example.slackr

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class GroupList(private val context: Activity, private var groups: List<Group>) : ArrayAdapter<Group>(context,
    R.layout.group_listitem, groups) {

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val groupListItem = inflater.inflate(R.layout.group_listitem, null, true)

        val groupName = groupListItem.findViewById<View>(R.id.group_name) as TextView
        val groupMembers = groupListItem.findViewById<View>(R.id.group_members) as TextView

        val group = groups[position]
        val name = group.groupName
        val id = group.groupId
        val description = group.groupDescription
        val location = group.groupLocation
        val subject = group.groupSubject
        val membersCount = group.groupMembers
        val members = group.members
        groupName.text = name
        groupMembers.text = ("$membersCount members")

        val postButton = groupListItem.findViewById<View>(R.id.group_post_button) as Button
        postButton.setOnClickListener {

            val postIntent = Intent(context, GroupPostsActivity::class.java)
            postIntent.putExtra("GroupName", name)
            postIntent.putExtra("GroupId", id)
            context.startActivity(postIntent)
        }

        val viewButton = groupListItem.findViewById<View>(R.id.group_view_button) as Button
        viewButton.setOnClickListener {

            val viewIntent = Intent(context, GroupViewActivity::class.java)
            viewIntent.putExtra("GroupName", name)
            viewIntent.putExtra("GroupId", id)
            viewIntent.putExtra("GroupDescription", description)
            viewIntent.putExtra("GroupLocation", location)
            viewIntent.putExtra("GroupSubject", subject)
            viewIntent.putExtra("GroupMembersCount", membersCount)
            viewIntent.putExtra("Members", members)

            context.startActivity(viewIntent)
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