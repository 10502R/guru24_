package com.example.guru24

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivityBadgeBinding

class BadgeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBadgeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBadgeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // "달성 뱃지 확인하기" 버튼 클릭 시 MainActivity로 이동하면서 "뱃지 프래그먼트 + 탭 정보 전달"
        binding.checkBadgeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("NAV_INDEX", TrophyFragment) // 네비게이션에서 뱃지 탭으로 이동
            intent.putExtra("TAB_INDEX", 2) // 뱃지 프래그먼트의 ViewPager2에서 2번 탭 선택
            startActivity(intent)
            finish() // 현재 액티비티 종료
        }
    }
}
