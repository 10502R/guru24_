package com.example.guru24

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivityStampInstructionsBinding

class StampInstructionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStampInstructionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStampInstructionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 확인 버튼 클릭 시 팝업 닫기
        binding.confirmButton.setOnClickListener {
            finish() // 현재 액티비티를 종료하여 팝업 닫기
        }
    }
}