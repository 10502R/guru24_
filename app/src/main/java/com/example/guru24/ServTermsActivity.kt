package com.example.guru24

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivityServTermsBinding

class ServTermsActivity : AppCompatActivity() {
    private var mBinding: ActivityServTermsBinding? = null
    private val binding get() = mBinding!!

    private var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityServTermsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // "동의합니다" 버튼 클릭 시 로그인 화면으로 이동
        binding.agreeServ.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // "닫기" 아이콘 클릭 시 Activity 종료 및 ActivityTermsOfService로 이동
        val closeIcon: ImageView = binding.closeIcon // closeIcon ID 사용
        closeIcon.setOnClickListener {
            // 현재 Activity 종료
            finish()
            // ActivityTermsOfService로 이동
            val intent = Intent(this, ActivityTermsOfService::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}

