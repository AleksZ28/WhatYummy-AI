package com.ali.whatyummyai

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.view.ViewGroup

class afterLoginButton : Fragment(R.layout.fragment_after_login_button) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_after_login_button, container, false)
        view.findViewById<Button>(R.id.bStartMain).setOnClickListener{
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
        return view
    }
}