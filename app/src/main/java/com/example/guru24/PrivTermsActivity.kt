package com.example.guru24

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivityPrivTermsBinding

class PrivTermsActivity : AppCompatActivity() {
    private var mBinding: ActivityPrivTermsBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityPrivTermsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // "동의합니다" 버튼 클릭 시 로그인 화면으로 이동
        binding.agreePriv.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}
