package com.azurowski.whatyummyai.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesRepository(private val context: Context) {

    companion object {
        val COOKING_INTRO_SHOWN = booleanPreferencesKey("cooking_intro_shown")
    }

    val isOnboardingShown: Flow<Boolean> = context.dataStore.data.map { preferences ->
            preferences[COOKING_INTRO_SHOWN] ?: false
        }

    suspend fun setOnboardingShown() {
        context.dataStore.edit { preferences ->
            preferences[COOKING_INTRO_SHOWN] = true
        }
    }

    suspend fun resetOnboardingShown() {
        context.dataStore.edit { preferences ->
            preferences[COOKING_INTRO_SHOWN] = false
        }
    }
}