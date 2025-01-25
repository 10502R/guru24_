package com.example.guru24

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivityMypageTermsBinding

class MypageTerms : AppCompatActivity() {
    private lateinit var binding: ActivityMypageTermsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageTermsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 'mypage_termsclose' 이미지 클릭 시 마이페이지로 돌아가기
        binding.mypageTermsclose.setOnClickListener {
            finish() // 현재 액티비티 종료
        }
    }
}

