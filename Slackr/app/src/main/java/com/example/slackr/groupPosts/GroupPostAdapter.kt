package com.example.slackr.groupPosts

import android.text.format.DateFormat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.slackr.R
import java.util.*

/** Some of the code was derived from Lab 07- Firebase App*/
internal class GroupPostAdapter(
    private val mPosts: List<GroupPost>,
    private val mRowLayout: Int
): RecyclerView.Adapter<GroupPostAdapter.ViewHolder>() {

    // Create ViewHolder which holds a View to be displayed
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(mRowLayout, viewGroup, false)
        return ViewHolder(v)
    }

    // Binding: The process of preparing a child view to display data corresponding to a position within the adapter.
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        // Getting all the data of the post
        val userName: String = mPosts[i].userName
        val postTitle: String = mPosts[i].pTitle
        val postDesc: String = mPosts[i].pDesc
        val postTime: String = mPosts[i].pTime

        // Handling time
        val calender = Calendar.getInstance(Locale.getDefault())
        calender.timeInMillis = postTime.toLong()
        val pTime = DateFormat.format("MM/dd/yyyy hh:mm", calender)

        // TODO - handle user image in a try catch block
        // Displaying the post data
        viewHolder.userName.text = userName
        viewHolder.postTitle.text = postTitle
        viewHolder.postDesc.text = postDesc
        viewHolder.postTime.text = pTime

    }

    override fun getItemCount(): Int {
        return mPosts.size
    }

    class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        //Get the fields of the holder
        internal val userName: TextView = itemView.findViewById(R.id.group_post_userName)
        internal val postTitle: TextView = itemView.findViewById(R.id.group_post_title)
        internal val postDesc: TextView = itemView.findViewById(R.id.group_post_description)
        internal val postTime: TextView = itemView.findViewById(R.id.group_post_time)

        override fun onClick(view: View) {
            // Display a Toast message indicting the selected item
            // TODO - Handle user clicking on post
        }
    }

}

