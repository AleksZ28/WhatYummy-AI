package com.azurowski.whatyummyai.main.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.azurowski.whatyummyai.main.model.KitchenItem
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class KitchenViewModel: ViewModel() {
    private val db = Firebase.firestore

    private val _kitchenItems = MutableStateFlow<List<KitchenItem>>(emptyList())
    val kitchenItems: StateFlow<List<KitchenItem>> = _kitchenItems
    private val user = FirebaseAuth.getInstance().currentUser
    private val userId = user!!.uid

    fun observeItems() {
        db.collection("users").document(userId).collection("kitchen_items")
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                val items = snapshot?.map { document ->
                    KitchenItem(
                        id = document.id,
                        name = document.getString("name") ?: ""
                    )
                } ?: emptyList()
                _kitchenItems.value = items
            }
    }

    fun addItem(name: String){
        db.collection("users").document(userId).collection("kitchen_items")
            .add(mapOf("name" to name))
            .addOnSuccessListener { documentReference ->
                Log.d("Firebase", "Dodano do bazy: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Firebase", "Błąd dodawania do bazy", e)
            }

    }

    fun deleteItem(id: String){
        db.collection("users").document(userId).collection("kitchen_items").document(id)
            .delete()
            .addOnSuccessListener { Log.d("Firebase", "Usunięto z bazy: ${id}") }
            .addOnFailureListener { e ->
                Log.w("Firebase", "Błąd usuwania z bazy", e)
            }

    }
}