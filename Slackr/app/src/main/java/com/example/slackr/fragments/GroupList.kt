package com.example.slackr.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.slackr.R

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

        return groupListItem
    }
}