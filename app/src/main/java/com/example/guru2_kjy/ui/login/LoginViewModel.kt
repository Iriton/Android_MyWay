package com.example.guru2_kjy.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.guru2_kjy.data.repository.LoginRepository
import com.example.guru2_kjy.data.Result

import com.example.guru2_kjy.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    ViewModel() {

    private val _loginForm = MutableStateFlow(LoginFormState())
    val loginFormState = _loginForm.asStateFlow()

    private val _loginResult = MutableStateFlow(LoginResult())
    val loginResult =  _loginResult.asStateFlow()

    val ioContext = Dispatchers.Default + SupervisorJob()

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        viewModelScope.launch(ioContext) {
            loginRepository.setLoginUserName(username = username, password = password)

            if (loginRepository.getLoginUserName().isNullOrEmpty()) {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            } else {
                _loginResult.value =
                    LoginResult(success = LoggedInUserView(displayName = loginRepository.getLoginUserName()))
            }
        }
    }

    fun setResultNull() {
        _loginResult.value = LoginResult()
    }

    fun loginDataChanged(username: String, password: String): Boolean {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } /*else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        }*/ else {
            _loginForm.value = LoginFormState(isDataValid = true)
            return true
        }

        return false
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}