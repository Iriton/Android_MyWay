package com.example.guru2_kjy.ui.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels // viewModels 임포트 추가
import androidx.appcompat.app.AlertDialog
import com.example.guru2_kjy.ui.login.Login
import com.example.guru2_kjy.R
import com.example.guru2_kjy.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Mypage : AppCompatActivity() {

    lateinit var userNameTextView: TextView
    private val viewModel: MyPageViewModel by viewModels() // ViewModel 인스턴스 생성

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mypage)

        userNameTextView = findViewById(R.id.userName)

        // TODO ViewModel에서 userName 값을 가져와서 텍스트뷰에 표시
        //val userName = LoginViewModel.userName
        val userName ="슈니"
        userNameTextView.text = userName

        // 로그로 확인
        Log.d("Mypage", "userName in SharedViewModel: $userName")

        // 뒤로가기 버튼 활성화
        val backButton = findViewById<ImageView>(R.id.back)
        backButton.setOnClickListener {
            onBackPressed() // 뒤로가기 기능 구현
        }

        // 탈퇴하기 버튼
        val secessionTextView = findViewById<TextView>(R.id.secessionBtn)
        secessionTextView.setOnClickListener {
            // 탈퇴 알림 대화상자 보여주기
            showSecessionAlert()
        }
    }

    private fun showSecessionAlert() {
        val dialogView = layoutInflater.inflate(R.layout.secession_popup, null)

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(dialogView)
        alertDialogBuilder.setCancelable(false)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        // 확인 버튼 클릭 이벤트 처리
        val confirmButton = dialogView.findViewById<ImageButton>(R.id.confirmButton)
        confirmButton.setOnClickListener {
            executeSecession() // 탈퇴 실행
            alertDialog.dismiss()
        }

        // 취소 버튼 클릭 이벤트 처리
        val cancelButton = dialogView.findViewById<ImageButton>(R.id.cancelButton)
        cancelButton.setOnClickListener {
            alertDialog.dismiss() // 알림 대화상자 닫기
        }
    }

    private fun executeSecession() {
        viewModel.deleteAll()
        // 이후 탈퇴 처리 완료 후 로그인 화면으로 이동
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish() // 마이페이지 액티비티 종료
    }
}
