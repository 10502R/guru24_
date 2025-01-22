package com.example.guru24

import DBHelper
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        // 예시: DB_student_info 테이블에 이메일과 학번 삽입
        dbHelper.insertStudentInfo("minjukim30604", 2023111741)
        dbHelper.insertStudentInfo("koyejun23", 2023111735)

        binding.ButtonCheckSignup.setOnClickListener {
            val email = binding.email.text.toString()
            val numberString = binding.number.text.toString()

            if (email.isNotBlank() && numberString.isNotBlank()) {
                val number = numberString.toIntOrNull()

                if (number != null) {
                    if (isEmailExists(email)) {
                        Toast.makeText(this, "겹치는 이메일입니다!", Toast.LENGTH_SHORT).show()
                    } else {
                        if (isEmailAndNumberInStudentInfo(email, number)) {
                            saveData(email, number) // DB_login에 데이터 저장
                            saveStudentInfo(email, number) // DB_student_info에 데이터 저장
                            val intent = Intent(this, PasswordActivity::class.java)
                            intent.putExtra("user_email", email)
                            startActivity(intent)

                            // 현재 액티비티 종료
                            finish()
                        } else {
                            Toast.makeText(this, "이메일과 학번 정보가 일치하지 않습니다!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "학번을 올바르게 입력하세요", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "모든 필드를 입력해주세요", Toast.LENGTH_SHORT).show()
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
        // dbHelper.close()는 불필요, SQLiteOpenHelper가 자동으로 처리합니다.
    }

    // 이메일과 학번 정보가 DB_student_info에 저장되도록 변경
    private fun saveStudentInfo(email: String, number: Int) {
        dbHelper.insertStudentInfo(email, number)  // DB_student_info에 데이터 삽입
    }

    // 데이터 저장 함수
    private fun saveData(email: String, number: Int) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("user_email", email)
            put("user_number", number)
        }

        try {
            val newRowId = db.insert("DB_login", null, values)
            if (newRowId != -1L) {
                Toast.makeText(this, "이메일과 학번 정보가 일치합니다 :)", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "데이터 저장 실패!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.e("DBHelper", "데이터 저장 중 오류 발생: ${e.message}")
            Toast.makeText(this, "데이터 저장 중 오류 발생: ${e.message}", Toast.LENGTH_SHORT).show()
        } finally {
            db.close()
        }
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

        val isExists = cursor.moveToFirst() // 결과가 있으면 true
        cursor.close()
        db.close()

        return isExists
    }

    // 이메일과 학번이 두 번째 테이블에 있는지 확인하는 메서드
    private fun isEmailAndNumberInStudentInfo(email: String, number: Int): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "DB_student_info", // 테이블명
            arrayOf("user_email", "user_number"), // 검색할 컬럼
            "user_email = ? AND user_number = ?", // WHERE 조건
            arrayOf(email, number.toString()), // ?에 들어갈 값
            null, null, null
        )

        val isExists = cursor.moveToFirst() // 결과가 있으면 true
        cursor.close()
        db.close()

        return isExists
    }
}
