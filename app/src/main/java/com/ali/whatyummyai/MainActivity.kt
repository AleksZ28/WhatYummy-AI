package com.ali.whatyummyai

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val rootLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)

        val prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val savedTheme = prefs.getInt("themeId", R.drawable.bg_gradient1)
        rootLayout.setBackgroundResource(savedTheme)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val searchbarFragment = SearchbarFragment()
        val mainFragment = MainFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fcvSearchbar, searchbarFragment)
            replace(R.id.fcvMain, mainFragment)
            commit()
        }
    }
}