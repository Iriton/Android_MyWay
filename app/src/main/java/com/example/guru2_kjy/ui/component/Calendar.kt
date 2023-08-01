package com.example.guru2_kjy.ui.component


import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.guru2_kjy.R
import com.example.guru2_kjy.ui.schedule.check.ScheduleCheck
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import org.threeten.bp.ZoneOffset
import org.threeten.bp.temporal.ChronoUnit

@AndroidEntryPoint
@Suppress("DEPRECATION")
class Calendar : AppCompatActivity() {
    private lateinit var calendarView: MaterialCalendarView
    private lateinit var selectButton: ImageView

    private val viewModel: CalendarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar)

        val selectImageKey = intent.getStringExtra("selectedImageKey") ?: ""

        lifecycleScope.launch(viewModel.ioContext) {
            viewModel.nextScreen.collectLatest {
                if (0 < it) {
                    launch(Dispatchers.Main) {
                        startActivity(it)
                    }
                }
            }
        }

        // 뒤로가기 버튼 활성화
        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            onBackPressed() // 뒤로가기 기능 구현
        }

        calendarView = findViewById(R.id.calendarView)
        selectButton = findViewById(R.id.select)

        val today: CalendarDay = CalendarDay.today()
        calendarView.selectedDate = today

        selectButton.setOnClickListener {
            val selectedDates = calendarView.selectedDates
            if (selectedDates.size >= 2) {
                val startDate =
                    selectedDates[0].date.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
                val endDate = selectedDates[selectedDates.size - 1].date.atStartOfDay()
                    .toInstant(ZoneOffset.UTC).toEpochMilli()


                val areaName = when (selectImageKey) {
                    "ko" -> "국내"
                    "ga" -> "가평"
                    "gang" -> "강릉"
                    "gyeng" -> "경주"
                    "bu" -> "부산"
                    "ye" -> "여수"
                    "inc" -> "인천"
                    "jeon" -> "전주"
                    "je" -> "제주"
                    "chun" -> "춘천"
                    "tae" -> "태안"
                    "tong" -> "통영"
                    "po" -> "포항"
                    else -> "국내"
                }

                viewModel.inputTravel(areaName, selectImageKey, startDate, endDate)
            }
        }
    }

    private fun startActivity(nid: Long) {
        val intent = Intent(this, ScheduleCheck::class.java)
        intent.putExtra("nid", nid)
        startActivity(intent)
        finish()
    }
}