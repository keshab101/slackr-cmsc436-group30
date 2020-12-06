package com.example.slackr.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.slackr.R
import com.example.slackr.searchGroupTypes.*

class SearchFragment : Fragment() {

    private lateinit var searchSubject: Button
    private lateinit var searchWeekly: Button
    private lateinit var searchTimely: Button
    private lateinit var searchEnvironment: Button
    private lateinit var searchLearningType: Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // Initialize the buttons
        searchSubject = view.findViewById(R.id.search_by_subject)
        searchWeekly = view.findViewById(R.id.search_by_week_days)
        searchTimely = view.findViewById(R.id.search_by_day_time)
        searchEnvironment = view.findViewById(R.id.search_by_study_env)
        searchLearningType = view.findViewById(R.id.search_by_learning_type)

        // Add set on click listeners with the search type and launch intent to specific classes
        searchSubject.setOnClickListener {
            startActivity(Intent(context, SearchSubject::class.java))
        }
        searchWeekly.setOnClickListener {
            startActivity(Intent(context, SearchWeekly::class.java))
        }
        searchTimely.setOnClickListener {
            startActivity(Intent(context, SearchDayTime::class.java))
        }
        searchEnvironment.setOnClickListener {
            startActivity(Intent(context, SearchEnvironment::class.java))
        }
        searchLearningType.setOnClickListener {
            startActivity(Intent(context, SearchLearningType::class.java))
        }

        return view
    }
}