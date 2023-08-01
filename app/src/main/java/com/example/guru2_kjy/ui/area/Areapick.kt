package com.example.guru2_kjy.ui.area

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.guru2_kjy.R
import com.example.guru2_kjy.ui.component.Calendar

class Areapick : AppCompatActivity() {
    lateinit var btn1 : ImageView
    lateinit var btn2 : ImageView
    lateinit var btn3 : ImageView
    lateinit var btn4 : ImageView
    lateinit var btn5 : ImageView
    lateinit var btn6 : ImageView
    lateinit var btn7 : ImageView
    lateinit var btn8 : ImageView
    lateinit var btn9 : ImageView
    lateinit var btn10 : ImageView
    lateinit var btn11 : ImageView
    lateinit var btn12 : ImageView
    lateinit var btn13 : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.areapick)

        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)
        btn10 = findViewById(R.id.btn10)
        btn11 = findViewById(R.id.btn11)
        btn12 = findViewById(R.id.btn12)
        btn13 = findViewById(R.id.btn13)

        btn1.setOnClickListener {
            setResultAndFinish()
            var intent = Intent(this, Calendar::class.java)
            intent.putExtra("selectedImageKey", "ko")
            startActivity(intent)
        }

        btn2.setOnClickListener {
            setResultAndFinish()
            var intent = Intent(this, Calendar::class.java)
            intent.putExtra("selectedImageKey", "ga")
            startActivity(intent)
        }

        btn3.setOnClickListener {
            setResultAndFinish()
            var intent = Intent(this, Calendar::class.java)
            intent.putExtra("selectedImageKey", "gang")
            startActivity(intent)
        }

        btn4.setOnClickListener {
            setResultAndFinish()
            var intent = Intent(this, Calendar::class.java)
            intent.putExtra("selectedImageKey", "gyeng")
            startActivity(intent)
        }

        btn5.setOnClickListener {
            setResultAndFinish()
            var intent = Intent(this, Calendar::class.java)
            intent.putExtra("selectedImageKey", "bu")
            startActivity(intent)
        }

        btn6.setOnClickListener {
            setResultAndFinish()
            var intent = Intent(this, Calendar::class.java)
            intent.putExtra("selectedImageKey", "ye")
            startActivity(intent)
        }

        btn7.setOnClickListener {
            setResultAndFinish()
            var intent = Intent(this, Calendar::class.java)
            intent.putExtra("selectedImageKey", "inc")
            startActivity(intent)
        }

        btn8.setOnClickListener {
            setResultAndFinish()
            var intent = Intent(this, Calendar::class.java)
            intent.putExtra("selectedImageKey", "jeon")
            startActivity(intent)
        }

        btn9.setOnClickListener {
            setResultAndFinish()
            var intent = Intent(this, Calendar::class.java)
            intent.putExtra("selectedImageKey", "je")
            startActivity(intent)
        }

        btn10.setOnClickListener {
            setResultAndFinish()
            var intent = Intent(this, Calendar::class.java)
            intent.putExtra("selectedImageKey", "chun")
            startActivity(intent)
        }

        btn11.setOnClickListener {
            setResultAndFinish()
            var intent = Intent(this, Calendar::class.java)
            intent.putExtra("selectedImageKey", "tae")
            startActivity(intent)
        }

        btn12.setOnClickListener {
            setResultAndFinish()
            var intent = Intent(this, Calendar::class.java)
            intent.putExtra("selectedImageKey", "tong")
            startActivity(intent)
        }

        btn13.setOnClickListener {
            setResultAndFinish()
            var intent = Intent(this, Calendar::class.java)
            intent.putExtra("selectedImageKey", "po")
            startActivity(intent)
        }
    }

    private fun setResultAndFinish() {
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}