package com.example.guru24

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private var mBinding: ActivitySignupBinding? = null
    private val binding get() = mBinding!!
    private lateinit var dbHelper: DBHelper

    companion object {
        const val REQUEST_CODE_PASSWORD = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        // DB_student_info 테이블에 이메일과 학번 삽입
        dbHelper.insertStudentInfo("minjukim30604", 2023111741)
        dbHelper.insertStudentInfo("koyejun23", 2023111735)
        dbHelper.insertStudentInfo("dayeon053", 2023111419)
        dbHelper.insertStudentInfo("bspart22", 2019120065)
        dbHelper.insertStudentInfo("freshman25", 2025111415)
        dbHelper.insertStudentInfo("signup25", 2025120041)

        // 초기 버튼 상태 설정 (비활성화)
        binding.ButtonCheckSignup.isEnabled = false
        binding.ButtonCheckSignup.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
        binding.ButtonUncheckSignup.visibility = android.view.View.VISIBLE
        binding.ButtonCheckSignup.visibility = android.view.View.GONE

        // 이메일 및 학번 입력 감지하여 버튼 활성화/비활성화
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = binding.email.text.toString()
                val number = binding.number.text.toString()

                if (email.isNotBlank() && number.isNotBlank()) {
                    binding.ButtonCheckSignup.isEnabled = true
                    binding.ButtonCheckSignup.setBackgroundColor(resources.getColor(R.color.black))
                    binding.ButtonUncheckSignup.visibility = android.view.View.GONE
                    binding.ButtonCheckSignup.visibility = android.view.View.VISIBLE
                } else {
                    binding.ButtonCheckSignup.isEnabled = false
                    binding.ButtonCheckSignup.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
                    binding.ButtonUncheckSignup.visibility = android.view.View.VISIBLE
                    binding.ButtonCheckSignup.visibility = android.view.View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.email.addTextChangedListener(textWatcher)
        binding.number.addTextChangedListener(textWatcher)

        binding.ButtonCheckSignup.setOnClickListener {
            val email = binding.email.text.toString()
            val numberString = binding.number.text.toString()

            if (email.isNotBlank() && numberString.isNotBlank()) {
                val number = numberString.toIntOrNull()

                if (number != null) {
                    if (isEmailExists(email)) {
                        Toast.makeText(this, "겹치는 이메일입니다!", Toast.LENGTH_SHORT).show()
                    } else if (isEmailAndNumberInStudentInfo(email, number)) {
                        // 🔹 디버깅 로그 추가
                        Log.d("SignupActivity", "이메일과 학번 확인 완료: 이메일=$email, 학번=$number")

                        // PasswordActivity로 이동하여 비밀번호를 입력받음
                        val intent = Intent(this, PasswordActivity::class.java)
                        intent.putExtra("user_email", email)
                        intent.putExtra("user_number", number)
                        startActivityForResult(intent, REQUEST_CODE_PASSWORD)
                    } else {
                        Toast.makeText(this, "이메일과 학번 정보가 일치하지 않습니다!", Toast.LENGTH_SHORT).show()
                        Log.e("SignupActivity", "이메일과 학번 정보 불일치: 이메일=$email, 학번=$number")
                    }
                } else {
                    Toast.makeText(this, "학번을 올바르게 입력하세요", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "모든 필드를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

        override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PASSWORD && resultCode == RESULT_OK) {
            val email = data?.getStringExtra("user_email") ?: return
            val number = data.getIntExtra("user_number", -1)
            val password = data.getStringExtra("user_password") ?: return

            if (email.isNotBlank() && number != -1 && password.isNotBlank()) {
                saveData(email, number, password)
            }
        }
    }

    private fun saveData(email: String, number: Int, password: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("user_email", email)
            put("user_number", number)
            put("user_password", password)
        }

        try {
            val newRowId = db.insert("DB_login", null, values)
            if (newRowId != -1L) {
                Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()
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

    private fun isEmailExists(email: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "DB_login",
            arrayOf("user_email"),
            "user_email = ?",
            arrayOf(email),
            null, null, null
        )

        val isExists = cursor.moveToFirst()
        cursor.close()
        db.close()

        return isExists
    }

    private fun isEmailAndNumberInStudentInfo(email: String, number: Int): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "DB_student_info",
            arrayOf("user_email", "user_number"),
            "user_email = ? AND user_number = ?",
            arrayOf(email, number.toString()),
            null, null, null
        )

        val isExists = cursor.moveToFirst()
        cursor.close()
        db.close()

        return isExists
    }
}
