package com.example.guru2_kjy.ui.schedule.input


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.guru2_kjy.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
@Suppress("DEPRECATION")
class ScheduleInput : AppCompatActivity() {
    private var selectedTime: String = ""
    private var selectedTransport: String = ""
    private var nid = 0L
    private var day = 0
    private lateinit var placeEditText:EditText
    private lateinit var moneyEditText:EditText
    private lateinit var otherEditText:EditText

    private val viewModel: ScheduleInputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_input)

        placeEditText = findViewById<EditText>(R.id.placeEditText)
        moneyEditText = findViewById<EditText>(R.id.moneyEditText)
        otherEditText = findViewById<EditText>(R.id.otherEditText)

        nid = intent.getLongExtra("nid", 0L)
        day = intent.getIntExtra("day", 0)

        lifecycleScope.launch(viewModel.ioContext) {
            viewModel.todoNid.collectLatest {
                if(0 < it) {
                    launch(Dispatchers.Main) {
                        addSchedule()
                    }
                }
            }
        }

        // 뒤로가기 버튼 활성화
        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            onBackPressed() // 뒤로가기 기능 구현
        }

        // moneyDeleteBtn 클릭 이벤트 리스너 설정 - 입력 텍스트 삭제
        val moneyDeleteBtn = findViewById<ImageView>(R.id.moneyDeleteBtn)
        moneyDeleteBtn.setOnClickListener {
            val moneyEditText = findViewById<EditText>(R.id.moneyEditText)
            moneyEditText.setText("")
        }

        // placeDeleteBtn 클릭 이벤트 리스너 설정 - 입력 텍스트 삭제
        val placeDeleteBtn = findViewById<ImageView>(R.id.placeDeleteBtn)
        placeDeleteBtn.setOnClickListener {
            placeEditText.setText("")
        }

        // 등록하기 버튼 누르면 스케줄 생성 함수 실행
        val btnReg = findViewById<ImageButton>(R.id.btnReg)
        btnReg.setOnClickListener {
            saveTodoItem()
        }

        // 시간 선택을 위한 TimePicker 설정
        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            selectedTime = String.format("%02d:%02d", hourOfDay, minute)
        }

        // 이동수단 선택을 위한 Spinner 설정
        val spinner = findViewById<Spinner>(R.id.spinner)
        val transportArray = resources.getStringArray(R.array.transport)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, transportArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedTransport = transportArray[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }


    private fun saveTodoItem() {
        val place = placeEditText.text.toString()
        val money = moneyEditText.text.toString()
        val other = otherEditText.text.toString()

        // selectedTime과 selectedTransport 변수를 사용하여 사용자가 선택한 시간과 이동수단을 가져옴
        val time = selectedTime
        val transport = selectedTransport

        viewModel.setToDoList(nid, day, place, time, transport, money, other)
    }

    // 입력받은 일정 정보를 일정 텍스트뷰에 추가하고 이전 액티비티로 돌아감
    private fun addSchedule() {
        val place = placeEditText.text.toString()
        val money = moneyEditText.text.toString()
        val other = otherEditText.text.toString()

        // selectedTime과 selectedTransport 변수를 사용하여 사용자가 선택한 시간과 이동수단을 가져옴
        val time = selectedTime
        val transport = selectedTransport

        val day = intent.getIntExtra("day", -1) // ScheduleCheck 액티비티로부터 day 정보 받아옴

        if (day != -1 && place.isNotEmpty()) {
            val intent = Intent()
            intent.putExtra("place", place)
            intent.putExtra("day", day) // day 정보를 다시 인텐트에 추가

            if (time.isNotEmpty()) {
                intent.putExtra("time", time)
            }

            if (money.isNotEmpty()) {
                intent.putExtra("money", money)
            }

            if (other.isNotEmpty()) {
                intent.putExtra("other", other)
            }

            // 이동수단 정보가 "미선택"이 아닌 경우에만 인텐트에 추가
            if (transport != "미선택") {
                intent.putExtra("transport", transport)
            }

            setResult(Activity.RESULT_OK, intent)
            finish()
        } else {
            // 일정 정보가 올바르게 입력되지 않은 경우에 대한 처리
            // 예를 들어, 사용자에게 일정 정보를 입력하도록 안내하는 토스트 메시지 출력 등
            // 여기에 추가적인 처리를 추가할 수 있습니다.
            Toast.makeText(this, "장소를 입력해 주세요.", Toast.LENGTH_SHORT).show()
        }
    }
}
