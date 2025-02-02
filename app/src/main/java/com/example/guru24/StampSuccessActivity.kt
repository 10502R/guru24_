package com.example.guru24

import android.os.Bundle
import android.view.WindowManager
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

//        // 배경 dimming 효과 설정
//        val window = window
//        val layoutParams = window.attributes
//        layoutParams.dimAmount = 0.5f
//        window.addFlags( WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        // 확인 버튼 클릭 시 팝업 닫기
        binding.confirmButtonSuccess.setOnClickListener {
            finish() // 현재 액티비티를 종료하여 팝업 닫기
        }
    }
}

