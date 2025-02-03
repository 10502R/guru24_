package com.example.guru24

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity() {
    private var mBinding: ActivityPasswordBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("user_email")
        val number = intent.getIntExtra("user_number", -1)

        // 초기 버튼 상태 설정 (비활성화)
        binding.ButtonCheckPassword.isEnabled = false
        binding.ButtonCheckPassword.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
        binding.ButtonUncheckPassword.visibility = android.view.View.VISIBLE
        binding.ButtonCheckPassword.visibility = android.view.View.GONE

        // 비밀번호 입력 감지
        binding.password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                if (isPasswordValid(password)) {
                    binding.ButtonCheckPassword.isEnabled = true
                    binding.ButtonCheckPassword.setBackgroundColor(resources.getColor(R.color.black))
                    binding.ButtonUncheckPassword.visibility = android.view.View.GONE
                    binding.ButtonCheckPassword.visibility = android.view.View.VISIBLE
                } else {
                    binding.ButtonCheckPassword.isEnabled = false
                    binding.ButtonCheckPassword.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
                    binding.ButtonUncheckPassword.visibility = android.view.View.VISIBLE
                    binding.ButtonCheckPassword.visibility = android.view.View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 확인 버튼 클릭 시
        binding.ButtonCheckPassword.setOnClickListener {
            val password = binding.password.text.toString()

            if (isPasswordValid(password)) {
                val resultIntent = Intent().apply {
                    putExtra("user_email", email)
                    putExtra("user_number", number)
                    putExtra("user_password", password)
                }
                setResult(RESULT_OK, resultIntent)

                // ✅ ActivityTermsOfService로 이동
                val termsIntent = Intent(this, ActivityTermsOfService::class.java)
                startActivity(termsIntent)

                finish() // 현재 액티비티 종료
            } else {
                Toast.makeText(this, "비밀번호 조건을 만족하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }


        // 로그인 텍스트 클릭 시 LoginActivity로 이동
        binding.login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // 비밀번호 유효성 검사
    private fun isPasswordValid(password: String): Boolean {
        val forbiddenChars = Regex("[;'\"--#/*\\s]") // 특수문자 및 공백 포함 여부 확인
        return if (password.length in 8..15 && !forbiddenChars.containsMatchIn(password)) {
            true
        } else {
            // 비밀번호 조건 미충족 시 토스트 메시지 출력
            Toast.makeText(this, "특수기호와 공백은 허용되지 않습니다.", Toast.LENGTH_SHORT).show()
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}
