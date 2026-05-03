package com.azurowski.whatyummyai.main.ui.screens.recipe

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azurowski.whatyummyai.main.model.AddRecipeState
import com.azurowski.whatyummyai.main.model.Ingredient
import com.azurowski.whatyummyai.main.model.IngredientField
import com.azurowski.whatyummyai.main.model.Recipe
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AddRecipeViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private val _state = MutableStateFlow(AddRecipeState())
    val state: StateFlow<AddRecipeState> = _state

    private val uploadPreset = "WhatYummy"

    fun saveRecipe(onSuccess: () -> Unit) {
        if (_state.value.isSaving) return

        val titleVal = _state.value.title.text.toString().trim()
        val timeVal = _state.value.totalTime.text.toString().trim()
        val hasIngredients = _state.value.ingredients.any { it.name.text.toString().isNotBlank() }
        val hasInstructions = _state.value.instructions.any { it.text.toString().isNotBlank() }

        if (titleVal.isEmpty() || timeVal.isEmpty() || !hasIngredients || !hasInstructions) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isSaving = true)

            try {
                val imageUrls = uploadAllImages(_state.value.images)
                val user = auth.currentUser
                val timeMultiplier = if (_state.value.timeUnit == "h") 60 else 1

                val newRecipe = Recipe(
                    title = _state.value.title.text.toString(),
                    ingredients = _state.value.ingredients.map {
                        Ingredient(
                            ingredient = it.name.text.toString(),
                            amount = it.amount.text.toString().toDoubleOrNull() ?: 0.0,
                            unit = it.unit
                        )
                    }.filter { it.ingredient.isNotEmpty() },
                    instructions = _state.value.instructions.map { it.text.toString() }.filter { it.isNotEmpty() },
                    categories = _state.value.categories,
                    totalMinutes = (_state.value.totalTime.text.toString().toIntOrNull() ?: 0) * timeMultiplier,
                    isGlutenFree = _state.value.glutenFree,
                    public = _state.value.public,
                    authorId = user?.uid ?: "",
                    authorName = user?.displayName ?: "",
                    imageUrls = imageUrls,
                )

                db.collection("recipes")
                    .add(newRecipe)
                    .addOnSuccessListener {
                        _state.value = _state.value.copy(isSaving = false)
                        onSuccess()
                    }.addOnFailureListener { e ->
                        Log.e("AddRecipe", e.toString())
                        _state.value = _state.value.copy(isSaving = false)
                    }

            } catch (e: Exception) {
                Log.e("AddRecipe", e.toString())
                _state.value = _state.value.copy(isSaving = false)
            }
        }
    }

    private suspend fun uploadAllImages(uris: List<Uri>): List<String> {
        return uris.map { uri ->
            viewModelScope.async { uploadSingleImage(uri) }
        }.awaitAll().filterNotNull()
    }

    private suspend fun uploadSingleImage(uri: Uri): String? = suspendCancellableCoroutine { cont ->
        MediaManager.get().upload(uri).unsigned(uploadPreset).callback(object : UploadCallback {
            override fun onSuccess(requestId: String?, resultData: Map<*, *>?) {
                cont.resume(resultData?.get("secure_url") as? String)
            }
            override fun onError(requestId: String?, error: ErrorInfo?) = cont.resume(null)
            override fun onReschedule(requestId: String?, error: ErrorInfo?) = cont.resume(null)
            override fun onStart(requestId: String?) {}
            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}
        }).dispatch()
    }

    fun addIngredient() {
        _state.value = _state.value.copy(ingredients = _state.value.ingredients + IngredientField())
    }

    fun updateIngredientUnit(index: Int, unit: String) {
        val currentIngredients = _state.value.ingredients.toMutableList()
        if (index in currentIngredients.indices) {
            currentIngredients[index] = currentIngredients[index].copy(unit = unit)
            _state.value = _state.value.copy(ingredients = currentIngredients)
        }
    }

    fun addInstruction() {
        _state.value = _state.value.copy(instructions = _state.value.instructions + TextFieldState())
    }

    fun updateTimeUnit(unit: String) {
        _state.value = _state.value.copy(timeUnit = unit)
    }

    fun addImages(uris: List<Uri>) {
        val remaining = 3 - state.value.images.size
        val toAdd = uris.take(remaining)
        _state.value = _state.value.copy(images = _state.value.images + toAdd)
    }

    fun removeImage(uri: Uri) {
        _state.value = _state.value.copy(images = _state.value.images - uri)
    }

    fun toggleCategory(category: String) {
        val currentCategories = _state.value.categories.toMutableList()
        if (currentCategories.contains(category)) {
            currentCategories.remove(category)
        } else {
            currentCategories.add(category)
        }
        _state.value = _state.value.copy(categories = currentCategories)
    }

    fun togglePublic() {
        _state.value = _state.value.copy(public = !_state.value.public)
    }

    fun toggleGlutenFree() {
        _state.value = _state.value.copy(glutenFree = !_state.value.glutenFree)
    }
}