package com.example.guru2_kjy.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.guru2_kjy.MyDatabaseHelper
import com.example.guru2_kjy.R
import com.example.guru2_kjy.ui.travel.Home
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Login : AppCompatActivity() {

    lateinit var loginButton: ImageButton
    lateinit var writeName: EditText
    lateinit var dbHelper: MyDatabaseHelper
    private val loginViewModel: LoginViewModel by viewModels() // ViewModel 인스턴스 생성

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        loginButton = findViewById(R.id.loginButton)
        writeName = findViewById(R.id.writeName)
        dbHelper = MyDatabaseHelper(this)

        loginButton.setOnClickListener {
            val name = writeName.text.toString().trim()

            if (loginViewModel.loginDataChanged(name, "")) {
                loginViewModel.login(name, "")
            } else {
                Toast.makeText(this, "닉네임을 작성해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch(loginViewModel.ioContext) {
            loginViewModel.loginResult.collectLatest {
                if(it.success?.displayName?.isNotEmpty() == true) {
                    launch(Dispatchers.Main) {
                    // TODO(NEXT_SCREEN)
                        loginViewModel.setResultNull()
                        val intent = Intent(this@Login, Home::class.java)
                        startActivity(intent)
                        Toast.makeText(this@Login, it.success.displayName+"님, 안녕하세요.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }
}
