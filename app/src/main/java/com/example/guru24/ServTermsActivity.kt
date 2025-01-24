package com.example.guru24

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivityServTermsBinding

class ServTermsActivity : AppCompatActivity() {
    private var mBinding: ActivityServTermsBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityServTermsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // "동의합니다" 버튼 클릭 시 체크 상태를 유지하고 약관 동의 페이지로 돌아감
        binding.agreeServ.setOnClickListener {
            val intent = Intent()
            intent.putExtra("isServiceChecked", true) // 서비스 체크 상태 업데이트
            setResult(RESULT_OK, intent)
            finish() // 현재 Activity 종료
        }

        // "닫기" 아이콘 클릭 시 약관 동의 페이지로 이동
        binding.closeIcon.setOnClickListener {
            finish() // 현재 Activity 종료
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}
