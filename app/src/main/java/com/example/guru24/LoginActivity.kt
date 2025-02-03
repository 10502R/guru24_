package com.example.guru24

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var mBinding: ActivityLoginBinding? = null
    private val binding get() = mBinding!!
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this) // DBHelper 인스턴스 초기화

        // 이메일과 비밀번호 입력 상태를 실시간으로 감지하기 위한 TextWatcher
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = binding.email.text.toString()
                val password = binding.password.text.toString()

                // 이메일과 비밀번호가 모두 입력되었을 경우 ButtonCheck 활성화
                if (email.isNotBlank() && password.isNotBlank()) {
                    binding.ButtonUncheck.visibility = View.GONE // ButtonUncheck 숨김
                    binding.ButtonCheck.visibility = View.VISIBLE // ButtonCheck 표시
                } else {
                    binding.ButtonUncheck.visibility = View.VISIBLE // ButtonUncheck 표시
                    binding.ButtonCheck.visibility = View.GONE // ButtonCheck 숨김
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        // TextWatcher를 이메일과 비밀번호 입력 필드에 추가
        binding.email.addTextChangedListener(textWatcher)
        binding.password.addTextChangedListener(textWatcher)

        // 기존 로그인 버튼 클릭 처리
        binding.ButtonCheck.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (email.isNotBlank() && password.isNotBlank()) {
                if (dbHelper.isValidLogin(email, password)) {
                    // 로그인 성공 시 MainActivity로 이동하며 이메일 전달
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("USER_EMAIL", email)
                    }
                    startActivity(intent)
                    finish() // 로그인 액티비티 종료
                } else {
                    Toast.makeText(this, "이메일 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 회원가입 버튼 클릭 시 SignupActivity로 이동
        binding.signup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null // 메모리 누수 방지
    }
}
