package com.example.guru2_kjy.data.repository

import com.example.guru2_kjy.data.model.LoggedInUser
import com.example.guru2_kjy.datastore.PrefDataStore
import com.example.guru2_kjy.datastore.PreferencesService

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(private val preferencesService: PreferencesService) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null    // 세션 관리?

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    fun logout() {
        user = null
        //data clear
        //preferencesService.putData(PrefDataStore.LOGIN_ID_KEY, "")
    }

    suspend fun setLoginUserName(username: String, password : String) {
        // handle login
        // local saved
        preferencesService.putData(PrefDataStore.LOGIN_ID_KEY, username)
        preferencesService.putData(PrefDataStore.LOGIN_PW_KEY, password)
    }

    suspend fun getLoginUserName(): String {
        return preferencesService.getString(PrefDataStore.LOGIN_ID_KEY)?: ""
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser

        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}