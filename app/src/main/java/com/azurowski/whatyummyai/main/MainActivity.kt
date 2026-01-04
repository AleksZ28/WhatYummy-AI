package com.azurowski.whatyummyai.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.azurowski.whatyummyai.R
import com.azurowski.whatyummyai.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val rootLayout = binding.main

        val prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val savedTheme = prefs.getInt("themeId", R.drawable.bg_gradient1)
        rootLayout.setBackgroundResource(savedTheme)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
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