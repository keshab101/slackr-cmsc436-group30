package com.example.slackr.searchGroupTypes

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.slackr.Group
import com.example.slackr.GroupList
import com.example.slackr.GroupListSearchResult
import com.example.slackr.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class SearchActivity: AppCompatActivity() {

    private var searchKey: String? = null
    private var searchOption: String? = null
    private var databaseRefHabit: DatabaseReference? = null
    private var databaseRefGroup: DatabaseReference? = null
    private lateinit var groups: MutableList<Group>
    private lateinit var groupList: ListView
    private lateinit var emptyTextView: TextView

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_group_results_listview)

        searchKey = intent.getStringExtra(SEARCH_TYPE)
        searchOption = intent.getStringExtra(SELECTED_BUTTON)
        databaseRefHabit = FirebaseDatabase.getInstance().getReference("studyHabits")
        databaseRefGroup = FirebaseDatabase.getInstance().getReference("groups")
        groupList = findViewById(R.id.result_group_list)
        emptyTextView = findViewById(R.id.empty_results_view)
        groupList.emptyView = emptyTextView
        groups = ArrayList()

        databaseRefHabit!!.addValueEventListener(object: ValueEventListener {
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
                    if (ds.child(searchKey!!).value.toString() == searchOption){

                        groupId = ds.key.toString()

                        databaseRefGroup!!.addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {

                                name = snapshot.child(groupId).child("groupName").value.toString()
                                membersCount = snapshot.child(groupId).child("groupMembers").value.toString()
                                members = snapshot.child(groupId).child("members").value as HashMap<String, String>
                                description = snapshot.child(groupId).child("groupDescription").value.toString()
                                location = snapshot.child(groupId).child("groupLocation").value.toString()
                                subject = snapshot.child(groupId).child("groupSubject").value.toString()
                                group = Group(groupId, name, membersCount, members, location, description, subject)
                                groups.add(group)

                                val groupResultAdapter = GroupListSearchResult(this@SearchActivity, groups)
                                groupList.adapter = groupResultAdapter
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    companion object {
        const val TAG = "Slacker-App"
        const val SEARCH_TYPE = "search_type"
        const val SELECTED_BUTTON = "selected_button"
    }
}