package com.example.guru2_kjy.ui.travel

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.guru2_kjy.R
import com.example.guru2_kjy.data.model.Travel
import com.example.guru2_kjy.ui.area.Areapick
import com.example.guru2_kjy.ui.mypage.Mypage
import com.example.guru2_kjy.ui.recommend.Recommend1
import com.example.guru2_kjy.ui.recommend.Recommend2
import com.example.guru2_kjy.ui.recommend.Recommend3
import com.example.guru2_kjy.ui.recommend.Recommend4
import com.example.guru2_kjy.ui.recommend.Recommend5
import com.example.guru2_kjy.ui.schedule.check.ScheduleCheck
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class Home : AppCompatActivity() {
    // 나중에 변수 타입 초기화. 선언
    lateinit var addTravel: ImageButton
    lateinit var MypageButton: ImageButton
    lateinit var recommend1: ImageButton
    lateinit var recommend2: ImageButton
    lateinit var recommend3: ImageButton
    lateinit var recommend4: ImageButton
    lateinit var recommend5: ImageButton

    // 버튼이 추가되는 레이어
    val containerLayout by lazy { findViewById<LinearLayout>(R.id.containerLayout) }

    // 동적으로 생성된 ImageButton을 담을 리스트 선언
    val dynamicButtons = mutableListOf<ImageButton>()

    private val homeViewModel: HomeViewModel by viewModels() // ViewModel 인스턴스 생성

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        // 연결
        addTravel = findViewById<ImageButton>(R.id.addTravel)
        MypageButton = findViewById<ImageButton>(R.id.MypageButton)
        recommend1 = findViewById<ImageButton>(R.id.recommend1)
        recommend2 = findViewById<ImageButton>(R.id.recommend2)
        recommend3 = findViewById<ImageButton>(R.id.recommend3)
        recommend4 = findViewById<ImageButton>(R.id.recommend4)
        recommend5 = findViewById<ImageButton>(R.id.recommend5)

        // 사용
        recommend1.setOnClickListener {
            var intent = Intent(this, Recommend1::class.java)
            startActivity(intent)
        }
        recommend2.setOnClickListener {
            var intent = Intent(this, Recommend2::class.java)
            startActivity(intent)
        }
        recommend3.setOnClickListener {
            var intent = Intent(this, Recommend3::class.java)
            startActivity(intent)
        }
        recommend4.setOnClickListener {
            var intent = Intent(this, Recommend4::class.java)
            startActivity(intent)
        }
        recommend5.setOnClickListener {
            var intent = Intent(this, Recommend5::class.java)
            startActivity(intent)
        }

        MypageButton.setOnClickListener {
            var intent = Intent(this, Mypage::class.java)
            startActivity(intent)
        }

        // 여행지 추가 버튼 클릭 시 Areapick 액티비티로 이동
        addTravel.setOnClickListener {
            startActivity(Intent(this, Areapick::class.java))
        }

        repeatOnStarted {
            homeViewModel.travelList.collectLatest {
                if (it.isNotEmpty()) {
                    launch(Dispatchers.Main) {
                        containerLayout.removeAllViews()
                        it.forEach {
                            createNewButton(it)
                        }
                    }
                }
            }
        }
    }

    // 이미지 키로부터 이미지 리소스 ID를 가져오는 함수
    private fun getImageResIdFromKey(imageKey: String): Int {
        return when (imageKey) {
            "ko" -> R.drawable.ko
            "ga" -> R.drawable.ga
            "gang" -> R.drawable.gang
            "gyeng" -> R.drawable.gyeng
            "bu" -> R.drawable.bu
            "ye" -> R.drawable.ye
            "inc" -> R.drawable.inc
            "jeon" -> R.drawable.jeon
            "je" -> R.drawable.je
            "chun" -> R.drawable.chun
            "tae" -> R.drawable.tae
            "tong" -> R.drawable.tong
            "po" -> R.drawable.po
            else -> R.drawable.ko
        }
    }

    // dp 값을 픽셀로 변환하는 함수
    private fun dpToPx(dp: Int): Int {
        val scale = resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    // 선택된 이미지 키를 가지고 새로운 버튼을 생성하는 함수
    private fun createNewButton(item: Travel.TravelItem) {
        // 선택된 이미지 키에 해당하는 이미지 리소스 ID를 가져옴
        val imageResId = getImageResIdFromKey(item.imageKey)

        // 새로운 버튼을 생성
        val newButton = ImageButton(this)
        newButton.id = View.generateViewId()
        newButton.setImageResource(imageResId as Int)

        val buttonLayoutParams = RelativeLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            dpToPx(130) // dp 값을 픽셀로 변환하여 크기를 설정
        )
        buttonLayoutParams.setMargins(0, dpToPx(5), 0, dpToPx(5))
        newButton.layoutParams = buttonLayoutParams

        // 새로운 버튼의 배경을 흰색으로 설정
        newButton.setBackgroundColor(resources.getColor(android.R.color.white))

        //이름 설정
        newButton.tag = "vist${dynamicButtons.size + 1}"


        // 새로운 TextView를 생성합니다.
        val newTextView = TextView(this)
        newTextView.text = "${item.startDate}\n ~ \n${item.endDate}"

        // 텍스트 색상을 흰색(white)으로 설정합니다.
        newTextView.setTextColor(resources.getColor(android.R.color.white))

        newTextView.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )

        // TextView의 위치를 ImageButton을 기준으로 설정합니다.
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(dpToPx(175), dpToPx(60), 0, 0) // 버튼과 텍스트뷰 사이의 여백을 설정합니다.
        newTextView.layoutParams = layoutParams

        // 새로운 버튼에 클릭 리스너를 추가
        newButton.setOnClickListener {
            // 새로운 버튼이 클릭되었을 때 수행할 동작: 일정체크로 넘어가기
            var intent = Intent(this, ScheduleCheck::class.java)
            intent.putExtra("nid", item.nid)
            startActivity(intent)
        }

        // 새로운 버튼과 TextView를 FrameLayout에 추가
        val frameLayout = FrameLayout(this)
        frameLayout.addView(newButton)
        frameLayout.addView(newTextView)

        // FrameLayout을 containerLayout에 index 0으로 추가합니다. (이전에 추가된 버튼들 위로 위치하게 됩니다.)
        containerLayout.addView(frameLayout, 0)

        // 새로운 버튼을 동적 버튼 목록에 index 0으로 추가합니다. (이전에 추가된 버튼들 위로 위치하게 됩니다.)
        dynamicButtons.add(0, newButton)
    }

    private fun LifecycleOwner.repeatOnStarted(
        context: CoroutineContext = homeViewModel.ioContext, block: suspend CoroutineScope.() -> Unit
    ) {
        lifecycleScope.launch(context) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }
}
