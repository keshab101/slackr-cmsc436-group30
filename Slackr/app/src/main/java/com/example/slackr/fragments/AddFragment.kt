package com.example.slackr.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.example.slackr.R


class AddFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add, container, false)

        val subjects = resources.getStringArray(R.array.subjects)
        val editText: AutoCompleteTextView = view.findViewById(R.id.group_subject)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            activity!!.applicationContext,
            R.layout.cg_custom_subject_item, R.id.text_view_list_item, subjects
        )
        editText.setAdapter(adapter)
        //editText.setThreshold(1);
        //get the input like for a normal EditText
        //String input = editText.getText().toString();


        return view;


    }


}