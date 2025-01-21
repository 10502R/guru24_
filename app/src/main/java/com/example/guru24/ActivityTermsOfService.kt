package com.example.guru24

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ActivityTermsOfService : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_of_service)

        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.rse1hzepg5bs))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.r5dehqajg09w))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.r67ab11dpoyg))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.rve2eycar1ub))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.rounrhz8d5u9))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.r2q1nehv6w4o))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.rqhyv5jn4fm))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.r01r2go573dpf))

        // 체크박스와 버튼 선언
        val checkAll = findViewById<CheckBox>(R.id.checkbox_all) // 전체 동의하기
        val checkTerms = findViewById<CheckBox>(R.id.checkbox_service) // 서비스 이용약관
        val checkPrivacy = findViewById<CheckBox>(R.id.checkbox_privacy) // 개인정보 처리방침
        val confirmButton = findViewById<Button>(R.id.check) // 확인 버튼

        // "전체 동의하기" 체크박스 클릭 시 다른 체크박스 상태 동기화
        checkAll.setOnCheckedChangeListener { _, isChecked ->
            checkTerms.isChecked = isChecked
            checkPrivacy.isChecked = isChecked
        }

        // 각 체크박스 상태에 따라 "전체 동의하기" 체크박스 상태 동기화
        checkTerms.setOnCheckedChangeListener { _, _ ->
            // 두 개의 체크박스가 모두 선택되었을 때만 "전체 동의하기" 체크박스를 체크
            checkAll.isChecked = checkTerms.isChecked && checkPrivacy.isChecked
        }

        checkPrivacy.setOnCheckedChangeListener { _, _ ->
            // 두 개의 체크박스가 모두 선택되었을 때만 "전체 동의하기" 체크박스를 체크
            checkAll.isChecked = checkTerms.isChecked && checkPrivacy.isChecked
        }

// "전체 동의하기" 체크박스 클릭 시 두 체크박스의 상태를 동기화
        checkAll.setOnCheckedChangeListener { _, isChecked ->
            checkTerms.isChecked = isChecked
            checkPrivacy.isChecked = isChecked
        }

// "확인" 버튼 클릭 시 모든 체크박스가 선택되었는지 확인
        confirmButton.setOnClickListener {
            if (checkTerms.isChecked && checkPrivacy.isChecked) {
                // 약관 화면으로 이동
                val intent = Intent(this, TermsActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "모든 필수 항목에 동의해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

// 각 체크박스를 클릭했을 때 해당 페이지로 이동하는 코드 추가
        checkTerms.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val intent = Intent(this, TermsActivity::class.java)
                startActivity(intent)
            }
            // 두 개의 체크박스가 모두 선택되었을 때만 "전체 동의하기" 체크박스를 체크
            checkAll.isChecked = checkTerms.isChecked && checkPrivacy.isChecked
        }

        checkPrivacy.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val intent = Intent(this, PrivacyPolicyActivity::class.java)
                startActivity(intent)
            }
            // 두 개의 체크박스가 모두 선택되었을 때만 "전체 동의하기" 체크박스를 체크
            checkAll.isChecked = checkTerms.isChecked && checkPrivacy.isChecked
        }

    }
}