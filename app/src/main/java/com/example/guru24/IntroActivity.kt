package com.example.guru24

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private var mBinding: ActivityIntroBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2초 후에 SingupActivity로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent) // MainActivity 시작
            finish() // IntroActivity 종료
        }, 2000) // 2000ms = 2초
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null // 메모리 누수 방지
    }
}
