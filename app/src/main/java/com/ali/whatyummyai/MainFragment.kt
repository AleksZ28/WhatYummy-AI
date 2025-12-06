package com.ali.whatyummyai

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ali.whatyummyai.adapters.KitchenAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rvKitchen)

        lateinit var adapter: KitchenAdapter
        val ingredients = mutableListOf<String>("Cebula", "Ogry")

        adapter = KitchenAdapter(ingredients) {}

        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

//        btnAddIngredient.setOnClickListener {
//            val newIngredient = etIngredient.text.toString()
//            if (newIngredient.isNotBlank()) {
//                adapter.addItem(newIngredient)
//                saveIngredientToFirebase(newIngredient)
//            }
//        }

        val fabAddIngredient = view.findViewById<ExtendedFloatingActionButton>(R.id.fabAddIngr)
        fabAddIngredient.setOnClickListener {
            val editText = EditText(requireContext()).apply {
                hint = "Wpisz składnik"
            }

            val container = FrameLayout(requireContext()).apply {
                setPadding(
                    50,
                    30,
                    50,
                    0
                )
                addView(editText)
            }

            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Dodaj składnik")
                .setView(container)
                .setPositiveButton("Dodaj") { dialog, _ ->
                    val newIngredient = editText.text.toString().trim()
                    if (newIngredient.isNotEmpty()) {
                        adapter.addItem(newIngredient)
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Anuluj") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }


        return view
    }

}