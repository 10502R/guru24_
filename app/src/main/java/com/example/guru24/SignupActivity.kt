package com.example.guru24

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private var mBinding: ActivitySignupBinding? = null
    private val binding get() = mBinding!!
    private lateinit var dbHelper: DBHelper // DBHelper 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.ButtonCheckSignup.setOnClickListener {
            val email = binding.email.text.toString()
            val numberString = binding.number.text.toString()

            // 이메일과 번호가 올바르게 입력되었는지 확인
            if (email.isNotBlank() && numberString.isNotBlank()) {
                val number = numberString.toIntOrNull() // 숫자로 변환

                if (number != null) {
                    if (isEmailExists(email)) {
                        Toast.makeText(this, "겹치는 이메일입니다!", Toast.LENGTH_SHORT).show()
                    } else {
                        saveData(email, number) // 데이터 저장
                        val intent = Intent(this, PasswordActivity::class.java)
                        intent.putExtra("user_email", email) // 이메일을 PasswordActivity로 전달
                        startActivity(intent)

                        // 현재 액티비티 종료
                        finish()
                    }
                } else {
                    Toast.makeText(this, "전화번호를 올바르게 입력하세요!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "모든 필드를 올바르게 입력하세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null // 메모리 누수 방지
        dbHelper.close() // DBHelper 자원 해제
    }

    // 데이터 저장 함수
    private fun saveData(email: String, number: Int) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("user_email", email)
            put("user_number", number)
        }

        val newRowId = db.insert("DB_login", null, values)
        if (newRowId != -1L) {
            Toast.makeText(this, "데이터 저장 성공!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "데이터 저장 실패!", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    // 이메일 중복 체크 함수
    private fun isEmailExists(email: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "DB_login", // 테이블명
            arrayOf("user_email"), // 검색할 컬럼
            "user_email = ?", // WHERE 조건
            arrayOf(email), // ?에 들어갈 값
            null, null, null
        )

        val isExists = cursor.count > 0 // 이메일이 이미 존재하면 true
        cursor.close()
        db.close()

        return isExists
    }
}
