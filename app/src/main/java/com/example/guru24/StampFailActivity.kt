package com.example.guru24

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivityStampFailBinding
import com.example.guru24.databinding.ActivityStampInstructionsBinding

class StampFailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStampFailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStampFailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 창의 배경을 투명하게 설정
        window.setBackgroundDrawableResource(android.R.color.transparent)

        // 확인 버튼 클릭 시 팝업 닫기
        binding.confirmButtonFail.setOnClickListener {
            finish() // 현재 액티비티를 종료하여 팝업 닫기
        }
    }
}