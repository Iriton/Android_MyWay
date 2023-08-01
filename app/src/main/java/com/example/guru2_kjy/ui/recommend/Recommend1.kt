package com.example.guru2_kjy.ui.recommend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import com.example.guru2_kjy.R
import com.example.guru2_kjy.ui.travel.Home

class Recommend1 : AppCompatActivity() {
    lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recommend1)

        back = findViewById<ImageButton>(R.id.back)

        back.setOnClickListener {
            var intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }
}