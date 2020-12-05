package com.example.slackr.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.example.slackr.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.lang.Exception

class HomeFragment : Fragment() {

    private lateinit var groupList: ListView
    private lateinit var groups: MutableList<Group>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var fireDatabase: FirebaseDatabase


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        groupList = view.findViewById(R.id.group_list)
        groups = ArrayList()
        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser!!
        fireDatabase = FirebaseDatabase.getInstance()


        val groupDatabase = fireDatabase.getReference("groups")
        groupDatabase.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var group: Group
                var name: String
                var groupId: String
                var membersCount: String
                var members: HashMap<String, String>
                var description: String
                var location: String
                var subject: String

                groups.clear()

                for (ds in snapshot.children) {
                    if (ds.child("members").child(user.uid).exists()){
                        name = ds.child("groupName").value.toString()
                        groupId = ds.child("groupId").value.toString()
                        membersCount = ds.child("groupMembers").value.toString()
                        members = ds.child("members").value as HashMap<String, String>
                        description = ds.child("groupDescription").value.toString()
                        location = ds.child("groupLocation").value.toString()
                        subject = ds.child("groupSubject").value.toString()
                        group = Group(groupId, name, membersCount, members, location, description, subject)
                        groups.add(group)
                    }
                }
                val groupAdapter = this@HomeFragment.activity?.let { GroupList(it, groups) }
                groupList.adapter = groupAdapter
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return view
    }

    companion object {
        const val TAG = "Slacker-App"
    }

}
