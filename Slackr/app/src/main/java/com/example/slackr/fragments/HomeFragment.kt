package com.example.slackr.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.slackr.Group
import com.example.slackr.GroupList
import com.example.slackr.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

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
                var members: String
                groups.clear()
                for (ds in snapshot.children) {
                    if (ds.child("members").child(user.uid).exists()){
                        name = ds.child("groupName").value.toString()
                        groupId = ds.child("groupId").value.toString()
                        members = ds.child("groupMembers").value.toString()
                        group = Group(groupId, name, members)
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
