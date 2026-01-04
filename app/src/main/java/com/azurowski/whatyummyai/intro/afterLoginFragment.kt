package com.azurowski.whatyummyai.intro

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import androidx.core.content.edit
import com.azurowski.whatyummyai.R

class afterLoginFragment : Fragment(R.layout.fragment_after_login) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_after_login, container, false)
        val user = FirebaseAuth.getInstance().currentUser
        val name = user?.displayName

        view.findViewById<TextView>(R.id.tvIntroH2).text = name

        val activity = requireActivity()
        val rootLayout = activity.findViewById<ConstraintLayout>(R.id.main)

        val prefs = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val circle1 = view.findViewById<View>(R.id.vThemeChoice1)
        val circle2 = view.findViewById<View>(R.id.vThemeChoice2)
        val circle3 = view.findViewById<View>(R.id.vThemeChoice3)

        val circles = listOf(circle1, circle2, circle3)

        circle1.isSelected = true

        circle1.setOnClickListener {
            circles.forEach {it.isSelected = false}
            circle1.isSelected = true
            rootLayout.setBackgroundResource(R.drawable.bg_gradient1)
            prefs.edit { putInt("themeId", R.drawable.bg_gradient1) }
        }
        circle2.setOnClickListener {
            circles.forEach {it.isSelected = false}
            circle2.isSelected = true
            rootLayout.setBackgroundResource(R.drawable.bg_gradient2)
            prefs.edit { putInt("themeId", R.drawable.bg_gradient2) }
        }
        circle3.setOnClickListener {
            circles.forEach {it.isSelected = false}
            circle3.isSelected = true
            rootLayout.setBackgroundResource(R.drawable.bg_gradient3)
            prefs.edit { putInt("themeId", R.drawable.bg_gradient3) }
        }

        return view
    }
}