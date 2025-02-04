package com.example.guru24

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity() {
    private var mBinding: ActivityPasswordBinding? = null
    private val binding get() = mBinding!!
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        val email = intent.getStringExtra("user_email")
        val number = intent.getIntExtra("user_number", -1)

        Log.d("PasswordActivity", "받은 이메일: $email, 받은 학번: $number") // 디버깅 로그

        binding.ButtonCheckPassword.setOnClickListener {
            val password = binding.password.text.toString()

            if (isPasswordValid(password)) {
                if (!email.isNullOrEmpty() && number != -1) {
                    // 🔹 DB에 사용자 정보 저장
                    if (dbHelper.saveUserData(email, number, password)) {
                        val termsIntent = Intent(this, ActivityTermsOfService::class.java)
                        startActivity(termsIntent)
                        finish() // 현재 액티비티 종료
                    } else {
                        Toast.makeText(this, "회원 정보 저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "이메일 또는 학번이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
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

    // 🔹 비밀번호 유효성 검사 (8~15자 길이 + 알파벳 + 숫자 포함)
    private fun isPasswordValid(password: String): Boolean {
        val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,15}$") // 알파벳 + 숫자 포함
        return regex.matches(password)
    }

    // 🔹 DB에 사용자 정보 저장 후 LoginActivity로 이동
    private fun saveUserData(email: String, number: Int, password: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("user_email", email)
            put("user_number", number)
            put("user_password", password)
        }

        try {
            // 중복된 경우 업데이트 처리
            val newRowId = db.insertWithOnConflict("DB_login", null, values, SQLiteDatabase.CONFLICT_REPLACE)

            if (newRowId != -1L) {
                // ✅ 저장된 데이터 로그 출력
                Log.d("DBHelper", "회원가입 성공: 이메일=$email, 학번=$number, 비밀번호=$password")

                // ✅ 회원가입 성공 메시지 출력
                Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()

                // ✅ LoginActivity로 이동
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
                finish() // 현재 액티비티 종료
            } else {
                Toast.makeText(this, "데이터 저장 실패!", Toast.LENGTH_SHORT).show()
                Log.e("DBHelper", "회원 정보 저장 실패")
            }
        } catch (e: Exception) {
            Toast.makeText(this, "데이터 저장 중 오류 발생: ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("DBHelper", "데이터 저장 중 오류 발생: ${e.message}")
        } finally {
            db.close()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}
