package com.example.guru24

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
class TermsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_of_service)
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.rg65yjcuvgjm))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.r1j1ws1vfn16))
        Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(findViewById(R.id.r58g84j0sxyg))

        val titleTextView: TextView = findViewById(R.id.tv_title)
        val contentTextView: TextView = findViewById(R.id.tv_content)

        // Intent에서 전달된 페이지 타입 확인
        val pageType = intent.getStringExtra("PAGE_TYPE")

        if (pageType == "terms") {
            // 서비스 이용약관 내용 설정
            titleTextView.text = "서비스 이용약관"
            contentTextView.text = getString(R.string.terms_content) // terms_content는 strings.xml에 정의된 텍스트
            Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(imageView)

        } else if (pageType == "privacy") {
            // 개인정보 처리방침 내용 설정
            titleTextView.text = "개인정보 처리방침"
            contentTextView.text = getString(R.string.privacy_content) // privacy_content는 strings.xml에 정의된 텍스트
            Glide.with(this).load("https://i.imgur.com/1tMFzp8.png").into(imageView)
        }


    }
}
