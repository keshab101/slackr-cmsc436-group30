package com.example.slackr.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import com.example.slackr.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class HomeFragment : Fragment() {

    private lateinit var viewButton: Button
    private lateinit var groupList: ListView
    private lateinit var groups: MutableList<Group>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var fireDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    //private lateinit var fStore: FirebaseFirestore
    //private lateinit var fStoreAuth: CollectionReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        groupList = view.findViewById(R.id.group_list)
        //viewButton = view.findViewById(R.id.group_view_button)
        groups = ArrayList()
        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser!!
        fireDatabase = FirebaseDatabase.getInstance()
        databaseReference = fireDatabase.getReference("users")
        //fStore = FirebaseFirestore.getInstance()
        //fStoreAuth = fStore.collection("users")

        //Find the current user
        //val q: Query = fStoreAuth
        val query: Query = databaseReference.orderByChild("email").equalTo(user.email)
        query.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var group: Group? = null
                for (ds in snapshot.children) {
                    try {
                        group = ds.getValue(Group::class.java)
                    } catch (e: Exception) {
                        Log.e(TAG, e.toString())
                    } finally {
                        groups.add(group!!)
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
