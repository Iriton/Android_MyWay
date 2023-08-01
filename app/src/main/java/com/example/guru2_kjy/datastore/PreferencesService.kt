package com.example.guru2_kjy.datastore

import androidx.datastore.preferences.core.Preferences

interface PreferencesService {

    suspend fun putData(key: Preferences.Key<*>, value: Any)
    suspend fun getString(key: Preferences.Key<String>): String
    suspend fun getInt(key: Preferences.Key<Int>, defValue: Int = 0): Int
    suspend fun getBoolean(key: Preferences.Key<Boolean>): Boolean
    suspend fun getLong(key: Preferences.Key<Long>): Long
    suspend fun getDouble(key: Preferences.Key<Double>): Double
}