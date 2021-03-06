package com.example.slackr.searchGroupTypes

import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.slackr.R
import com.example.slackr.groupDisplay.Group
import com.example.slackr.groupDisplay.GroupListSearchResult
import com.google.firebase.database.*

class SearchActivity: AppCompatActivity() {

    private lateinit var searchKey: String
    private lateinit var searchOption: String
    private lateinit var databaseRefHabit: DatabaseReference
    private lateinit var databaseRefGroup: DatabaseReference
    private lateinit var groups: MutableList<Group>
    private lateinit var groupList: ListView
    private lateinit var emptyTextView: TextView

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_group_results_listview)

        searchKey = intent.getStringExtra(SEARCH_TYPE).toString()
        searchOption = intent.getStringExtra(SELECTED_BUTTON).toString()
        databaseRefHabit = FirebaseDatabase.getInstance().getReference("studyHabits")
        databaseRefGroup = FirebaseDatabase.getInstance().getReference("groups")
        groupList = findViewById(R.id.result_group_list)
        emptyTextView = findViewById(R.id.empty_results_view)
        groups = ArrayList()

        databaseRefHabit.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                groups.clear()
                var groupId: String
                for(ds in snapshot.children) {
                    if (ds.child(searchKey).value.toString() == searchOption){
                        groupId = ds.key.toString()

                        var group: Group
                        var name: String
                        var membersCount: String
                        var members: HashMap<String, String> = HashMap()
                        var description: String
                        var location: String
                        var subject: String
                        databaseRefGroup.child(groupId).addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onDataChange(snap: DataSnapshot) {

                                //Get all the group info
                                name = snap.child("groupName").value.toString()
                                membersCount = snap.child("groupMembers").value.toString()
                                members = snap.child("members").value as HashMap<String, String>
                                description = snap.child("groupDescription").value.toString()
                                location = snap.child("groupLocation").value.toString()
                                subject = snap.child("groupSubject").value.toString()

                                group = Group(groupId, name, membersCount, members, location, description, subject)
                                groups.add(group)

                                // Make the listView display
                                if (groups.isEmpty()) {
                                    groupList.emptyView = emptyTextView
                                } else {
                                    emptyTextView.visibility = View.GONE
                                }
                                val groupResultAdapter = GroupListSearchResult(this@SearchActivity, groups)
                                groupList.adapter = groupResultAdapter
                            }

                            override fun onCancelled(error: DatabaseError) {
                                // Nothing to do here
                            }

                        })

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Nothing to do here
            }
        })
    }

    companion object {
        const val TAG = "Slacker-App"
        const val SEARCH_TYPE = "search_type"
        const val SELECTED_BUTTON = "selected_button"
    }
}