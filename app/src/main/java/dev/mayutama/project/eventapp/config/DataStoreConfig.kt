package dev.mayutama.project.eventapp.config

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("setting")

class DataStoreConfig private constructor(private val dataStore: DataStore<Preferences>){
    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { prefereces ->
            prefereces[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkMode: Boolean){
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkMode
        }
    }

    fun getReminderSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[REMINDER_KEY] ?: false
        }
    }

    suspend fun saveReminderSetting(isReminder: Boolean) {
        dataStore.edit { preferences ->
            preferences[REMINDER_KEY] = isReminder
        }
    }

    companion object{
        private val THEME_KEY = booleanPreferencesKey("theme_setting")
        private val REMINDER_KEY = booleanPreferencesKey("reminder_setting")

        @Volatile
        private var INSTANCE:DataStoreConfig? = null

        fun getInstance(dataStore: DataStore<Preferences>): DataStoreConfig {
            return INSTANCE ?: synchronized(this){
                INSTANCE = DataStoreConfig(dataStore)
                INSTANCE as DataStoreConfig
            }
        }
    }
}