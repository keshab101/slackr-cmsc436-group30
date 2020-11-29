package com.example.slackr.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.slackr.R
import com.example.slackr.GroupDetailActivity

class GroupList(private val context: Activity, private var groups: List<Group>) : ArrayAdapter<Group>(context,
    R.layout.group_list, groups) {

    @SuppressLint("InflateParams", "ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val groupListItem = inflater.inflate(R.layout.group_list, null, true)

        val groupName = groupListItem.findViewById<View>(R.id.group_name) as TextView
        val groupMembers = groupListItem.findViewById<View>(R.id.group_members) as TextView

        val group = groups[position]
        groupName.text = group.groupName
        groupMembers.text = group.groupMembers.toString() + " members"

        val groupButton = groupListItem.findViewById<View>(R.id.group_view_button) as Button
        groupButton.setOnClickListener {

            val intent = Intent(context, GroupDetailActivity::class.java)
            intent.putExtra("GroupName", group.groupName)
            intent.putExtra("GroupMembers", group.groupMembers.toString())
            intent.putExtra("GroupID", group.groupId)
            context.startActivity(intent)

        }

        return groupListItem
    }

    override fun getItem(position: Int): Group {
        return groups[position]
    }
}