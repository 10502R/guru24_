package com.example.guru24

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivityStampSuccessBinding


class StampSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStampSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStampSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 창의 배경을 투명하게 설정
        window.setBackgroundDrawableResource(android.R.color.transparent)

        // 확인 버튼 클릭 시 팝업 닫기
        binding.confirmButtonSuccess.setOnClickListener {
            finish() // 현재 액티비티를 종료하여 팝업 닫기
        }
    }
}

