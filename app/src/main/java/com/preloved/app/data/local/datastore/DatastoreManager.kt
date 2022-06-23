package com.preloved.app.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatastoreManager(private val context: Context) {

    companion object {
        private const val USERPREF = "USER_PREFS"
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN_KEY")
        private val EMAIL_KEY = stringPreferencesKey("EMAIL_KEY")
        const val DEFAULT_ACCESS_TOKEN = "DEF_ACCESS_TOKEN"
        const val DEFAULT_EMAIL = "DEF_EMAIL"
        val Context.dataStore by preferencesDataStore(USERPREF)
    }

    suspend fun setUser(datastorePreferences: DatastorePreferences) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = datastorePreferences.access_token
            preferences[EMAIL_KEY] = datastorePreferences.email
        }
    }

    fun getUser(): Flow<DatastorePreferences> {
        return context.dataStore.data.map { preferences ->
            DatastorePreferences(
                preferences[ACCESS_TOKEN_KEY] ?: DEFAULT_ACCESS_TOKEN,
                preferences[EMAIL_KEY] ?: DEFAULT_EMAIL
            )
        }
    }

    suspend fun deleteUserFromPref() {
        context.dataStore.edit {
            it.clear()
        }
    }

}