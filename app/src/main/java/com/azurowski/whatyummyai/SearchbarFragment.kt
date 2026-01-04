package com.azurowski.whatyummyai

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

class SearchbarFragment : Fragment(R.layout.fragment_searchbar) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_searchbar, container, false)

            val etSearch = view.findViewById<EditText>(R.id.etSearchbar).setOnClickListener{
                startActivity(Intent(requireContext(), SearchActivity::class.java))
            }

        return view
    }
}