package com.example.guru2_kjy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.os.Handler
import android.widget.ImageButton
import com.example.guru2_kjy.ui.login.Login

class MainActivity : AppCompatActivity() {
    //추후에 초기화 변수 타입. 선언
    lateinit var startButton: ImageButton
    private lateinit var backgroundImage1: ImageView
    private lateinit var backgroundImage2: ImageView
    private var isImage1Visible = true

    private val handler = Handler()
    private val imageSwitchRunnable = object : Runnable {

        override fun run() {
            // Switch the visibility of the two images
            if (isImage1Visible) {
                backgroundImage1.visibility = ImageView.INVISIBLE
                backgroundImage2.visibility = ImageView.VISIBLE
            } else {
                backgroundImage1.visibility = ImageView.VISIBLE
                backgroundImage2.visibility = ImageView.INVISIBLE
            }

            isImage1Visible = !isImage1Visible // Toggle the flag

            handler.postDelayed(this, 3000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main) //화면 불러오기

        //연결
        backgroundImage1 = findViewById(R.id.backgroundImage1)
        backgroundImage2 = findViewById(R.id.backgroundImage2)
        startButton = findViewById<ImageButton>(R.id.startButton)

        startImageSwitchLoop()

        //사용
        startButton.setOnClickListener {
            var intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
    private fun startImageSwitchLoop() {
        // Start the image switch loop with an initial delay of 3 seconds
        handler.postDelayed(imageSwitchRunnable, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove the image switch runnable when the activity is destroyed to avoid memory leaks
        handler.removeCallbacks(imageSwitchRunnable)
    }
}