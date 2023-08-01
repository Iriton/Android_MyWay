package com.example.guru2_kjy.ui.schedule.check

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.NavUtils
import androidx.lifecycle.lifecycleScope
import com.example.guru2_kjy.R
import com.example.guru2_kjy.data.model.Travel
import com.example.guru2_kjy.ui.schedule.input.ScheduleInput
import com.example.guru2_kjy.ui.travel.Home
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
@Suppress("DEPRECATION")
class ScheduleCheck : AppCompatActivity() {

    private val REQUEST_ADD_SCHEDULE = 1001 // ScheduleInputActivity로 이동하는 요청 코드

    private val viewModel: ScheduleCheckViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_check)

        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)

        viewModel.getTravel(intent.getLongExtra("nid", 0L))
        viewModel.getToDoList(intent.getLongExtra("nid", 0L))
        lifecycleScope.launch(viewModel.ioContext) {
            viewModel.list.collectLatest {
                launch(Dispatchers.Main) {
                    linearLayout.removeAllViews()
                    var dayCheck = -1
                    it.forEach { item ->

                        if (dayCheck != item.day) {
                            dayCheck = item.day
                            linearLayout.addView(createTravelHeaderTextView(item.day))
                        }

                        if (0 < item.nid) {
                            val scheduleTextView =
                                createScheduleLinearLayout(
                                    item.place,
                                    item.todoTime,
                                    item.money,
                                    item.memo,
                                    item.transport
                                )

                            // 해당 일정이 추가되기 전에 간격을 줄 Space를 추가
                            val space = Space(this@ScheduleCheck)
                            val layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                dpToPx(8)
                            )
                            space.layoutParams = layoutParams
                            linearLayout.addView(space)

                            linearLayout.addView(scheduleTextView)
                        } else {
                            val space = Space(this@ScheduleCheck)
                            val layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                dpToPx(8)
                            )
                            space.layoutParams = layoutParams
                            linearLayout.addView(space)

                            val textViewLayout = createTravelTextView(item)
                            linearLayout.addView(textViewLayout)
                        }
                    }
                }
            }
        }

        // 뒤로가기 버튼 활성화
        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            onBackPressed() // 뒤로가기 기능 구현
        }

        // Register 버튼 클릭 이벤트 처리
        val registerButton = findViewById<ImageButton>(R.id.registerBtn)
        registerButton.setOnClickListener {
            onRegisterButtonClick()
            var intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        // 뒤로가기 버튼을 눌렀을 때 부모 액티비티로 이동
        NavUtils.navigateUpFromSameTask(this)
        return true
    }

    // ImageButton을 클릭할 때 PopupMenu를 표시하는 함수
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu_check, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_edit -> {
                    // TODO 편집 동작을 처리
                    // 장소 추가 페이지로 돌아가서 재입력
                    true
                }

                R.id.menu_delete -> {
                    // TODO 삭제 동작 처리
                    // 레이아웃 삭제
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    // Register 버튼 클릭 시 호출되는 함수
    private fun onRegisterButtonClick() {
        Toast.makeText(this, "일정이 저장되었습니다.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
    }

    // 여행 일정 헤더뷰 생성
    private fun createTravelHeaderTextView(day: Int): ConstraintLayout {
        val layoutInflater = layoutInflater
        val textViewLayout =
            layoutInflater.inflate(R.layout.item_travel_header, null) as ConstraintLayout
        textViewLayout.findViewById<TextView>(R.id.dayTextView).text = "${day + 1}일차"
        // val scheduleLayout = textViewLayout.findViewById<LinearLayout>(R.id.scheduleLayout)

        return textViewLayout
    }

    // 여행 일정 텍스트뷰 생성
    private fun createTravelTextView(item: Travel.TravelToDoList): LinearLayout {
        val layoutInflater = layoutInflater
        val textViewLayout = layoutInflater.inflate(R.layout.item_travel, null) as LinearLayout
        val addScheduleButton = textViewLayout.findViewById<ImageButton>(R.id.addScheduleButton)

        // 여행 일정 추가 버튼의 클릭 이벤트 처리
        addScheduleButton.setOnClickListener {
            // ScheduleInputActivity로 이동
            val intent = Intent(this, ScheduleInput::class.java)
            intent.putExtra("nid", viewModel.travel.value.nid) // 해당 nid 추가
            intent.putExtra("day", item.day) // 해당 일차 정보를 인텐트에 추가
            startActivity(intent)
        }

        return textViewLayout
    }

    private fun createScheduleLinearLayout(
        place: String,
        time: String,
        money: String,
        other: String,
        transport: String
    ): LinearLayout {
        val layoutInflater = layoutInflater
        val scheduleLayout = layoutInflater.inflate(R.layout.item_schedule, null) as LinearLayout


        // 장소 입력 내용 설정
        val placeTextView = scheduleLayout.findViewById<TextView>(R.id.placeEditText)
        placeTextView?.text = place // 수정: null인 경우를 고려하여 옵셔널 체크

        // 시간 입력 내용 설정
        val timeTextView = scheduleLayout.findViewById<TextView>(R.id.timeEditText)
        if (time.isNotEmpty()) {
            timeTextView.text = time
        } else {
            val time = scheduleLayout.findViewById<TextView>(R.id.time)
            time.visibility = View.GONE
            timeTextView.visibility = View.GONE
        }

        // 예산 입력 내용 설정
        val moneyTextView = scheduleLayout.findViewById<TextView>(R.id.moneyEditText)
        val moneyWon = scheduleLayout.findViewById<TextView>(R.id.won)
        if (money.isNotEmpty()) {
            moneyTextView.text = "$money"
        } else {
            val money = scheduleLayout.findViewById<TextView>(R.id.money)
            money.visibility = View.GONE
            moneyTextView.visibility = View.GONE
            moneyWon.visibility = View.GONE
        }

        // 메모 입력 내용 설정
        val otherTextView = scheduleLayout.findViewById<TextView>(R.id.otherEditText)
        if (other.isNotEmpty()) {
            otherTextView.text = other
        } else {
            val other = scheduleLayout.findViewById<TextView>(R.id.other)
            other.visibility = View.GONE
            otherTextView.visibility = View.GONE
        }

        // 이동수단 입력 내용 설정
        val transportTextView = scheduleLayout.findViewById<TextView>(R.id.transportEditText)
        if (transport.isNotEmpty()) {
            transportTextView.text = transport
        } else {
            val transport = scheduleLayout.findViewById<TextView>(R.id.transport)
            transport.visibility = View.GONE
            transportTextView.visibility = View.GONE
        }

        // 팝업 메뉴 버튼 클릭 이벤트 처리
        val popupMenuButton = scheduleLayout.findViewById<ImageButton>(R.id.editPopupMenu)
        popupMenuButton.setOnClickListener {
            // 팝업 메뉴 표시
            showPopupMenu(popupMenuButton)
        }

        return scheduleLayout
    }

    // 확장 함수를 사용하여 dp를 픽셀(px)로 변환
    private fun dpToPx(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

}
