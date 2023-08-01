package com.example.guru2_kjy.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.prolificinteractive.materialcalendarview.BuildConfig
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PrefDataStore @Inject constructor(
    private val context: Context,
) : PrefDataStoreService {

    private val Context.dataStore by preferencesDataStore(name = BuildConfig.APPLICATION_ID)

    override suspend fun putString(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    override suspend fun getString(key: Preferences.Key<String>): String {
        return try {
            val preference = context.dataStore.data.first()
            return preference[key] ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    override suspend fun putInt(key: Preferences.Key<Int>, value: Int) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    override suspend fun getInt(key: Preferences.Key<Int>, defValue: Int): Int {
        return try {
            val preference = context.dataStore.data.first()
            return preference[key] ?: defValue
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    override suspend fun putLong(key: Preferences.Key<Long>, value: Long) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    override suspend fun getLong(key: Preferences.Key<Long>): Long {
        return try {
            val preference = context.dataStore.data.first()
            return preference[key] ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    override suspend fun putBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    override suspend fun getBoolean(key: Preferences.Key<Boolean>): Boolean {
        return try {
            val preference = context.dataStore.data.first()
            return preference[key] ?: false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun putDouble(key: Preferences.Key<Double>, value: Double) {
        context.dataStore.edit {
            it[key] = value
        }
    }

    override suspend fun getDouble(key: Preferences.Key<Double>): Double {
        return try {
            val preference = context.dataStore.data.first()
            return preference[key] ?: 0.0
        } catch (e: Exception) {
            e.printStackTrace()
            0.0
        }
    }

    override suspend fun putLoginIdPw(id: String, pw: String) {
        context.dataStore.edit { preferences ->
            preferences[LOGIN_ID_KEY] = id
            preferences[LOGIN_PW_KEY] = pw
        }
    }

    companion object {
        val LOGIN_ID_KEY = stringPreferencesKey("LoginId")
        val LOGIN_PW_KEY = stringPreferencesKey("LoginPw")
    }
}