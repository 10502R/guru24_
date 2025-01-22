package com.example.guru24

import DBHelper
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity() {
    private var mBinding: ActivityPasswordBinding? = null
    private val binding get() = mBinding!!
    private lateinit var dbHelper: DBHelper // DBHelper 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        // 이메일을 Intent로 받기
        val email = intent.getStringExtra("user_email")

        binding.ButtonCheckPassword.setOnClickListener {
            val password = binding.password.text.toString()

            if (password.isNotBlank()) {
                if (email != null) {
                    // 비밀번호 저장
                    savePassword(email, password)

                    // 비밀번호 저장 성공 후 메시지 띄우기
                    Toast.makeText(this, "비밀번호가 저장되었습니다!", Toast.LENGTH_SHORT).show()

                    // LoginActivity로 이동
                    val intent = Intent(this, ActivityTermsOfService::class.java)
                    startActivity(intent)

                    // 현재 액티비티 종료
                    finish()
                } else {
                    Toast.makeText(this, "유효한 이메일 정보가 없습니다!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show()
            }
        }

        // 로그인 버튼 클릭 시 SignupActivity로 이동
        binding.login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null // 메모리 누수 방지
        dbHelper.close() // DBHelper 자원 해제
    }

    private fun savePassword(email: String, password: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("user_email", email)
            put("user_password", password)
        }

        // 이메일이 이미 존재하면 업데이트, 아니면 삽입
        val rowId = db.insertWithOnConflict("DB_login", null, values, SQLiteDatabase.CONFLICT_REPLACE)

        if (rowId != -1L) {
            Toast.makeText(this, "비밀번호 저장 성공!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "비밀번호 저장 실패!", Toast.LENGTH_SHORT).show()
        }

        db.close()
    }
}
