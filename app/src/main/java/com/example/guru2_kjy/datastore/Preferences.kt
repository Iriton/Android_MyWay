package com.example.guru2_kjy.datastore

import javax.inject.Inject
import androidx.datastore.preferences.core.Preferences

class Preferences @Inject constructor(
    private val preferenceService: PrefDataStoreService) : PreferencesService{

    override suspend fun putData(key: Preferences.Key<*>, value: Any) {
        when(value) {
            is String -> {
                preferenceService.putString((key as Preferences.Key<String>), value)
            }
            is Int -> {
                preferenceService.putInt((key as Preferences.Key<Int>), value)
            }
            is Boolean -> {
                preferenceService.putBoolean((key as Preferences.Key<Boolean>), value)
            }
            is Long -> {
                preferenceService.putLong((key as Preferences.Key<Long>), value)
            }
            is Double -> {
                preferenceService.putDouble((key as Preferences.Key<Double>), value)
            }
        }
    }

    override suspend fun getString(key: Preferences.Key<String>): String {
        return preferenceService.getString(key)
    }

    override suspend fun getInt(key: Preferences.Key<Int>, defValue: Int): Int {
        return preferenceService.getInt(key, defValue)
    }

    override suspend fun getBoolean(key: Preferences.Key<Boolean>): Boolean {
        return preferenceService.getBoolean(key)
    }

    override suspend fun getLong(key: Preferences.Key<Long>): Long {
        return preferenceService.getLong(key)
    }

    override suspend fun getDouble(key: Preferences.Key<Double>): Double {
        return preferenceService.getDouble(key)
    }
}