package com.example.guru24

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivityBadgeBinding

class BadgeActivity : AppCompatActivity() {

    private var mBinding: ActivityBadgeBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityBadgeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkBadgeButton.setOnClickListener {
            // "달성 뱃지 확인하기" 버튼 클릭 시 TrophyFragment의 BadgeFragment로 이동
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("navigateTo", "TrophyFragment")
            intent.putExtra("selectedTab", 1) // TabLayout 두 번째 탭 (인덱스 1)
            startActivity(intent)
            finish() // 현재 액티비티 종료
        }

        binding.closeButton.setOnClickListener {
            // 닫기 버튼 클릭 시 BadgeActivity 닫기
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null // 메모리 누수 방지
    }
}