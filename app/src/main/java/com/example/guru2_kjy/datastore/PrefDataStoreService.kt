package com.example.guru2_kjy.datastore

import androidx.datastore.preferences.core.Preferences

interface PrefDataStoreService {

    suspend fun putLoginIdPw(id: String, pw: String)
    suspend fun putString(key: Preferences.Key<String>, value: String)
    suspend fun getString(key: Preferences.Key<String>): String
    suspend fun putInt(key: Preferences.Key<Int>, value: Int)
    suspend fun getInt(key: Preferences.Key<Int>, defValue: Int = 0): Int
    suspend fun putBoolean(key: Preferences.Key<Boolean>, value: Boolean)
    suspend fun getBoolean(key: Preferences.Key<Boolean>): Boolean
    suspend fun putLong(key: Preferences.Key<Long>, value: Long)
    suspend fun getLong(key: Preferences.Key<Long>): Long
    suspend fun putDouble(key: Preferences.Key<Double>, value: Double)
    suspend fun getDouble(key: Preferences.Key<Double>): Double
}