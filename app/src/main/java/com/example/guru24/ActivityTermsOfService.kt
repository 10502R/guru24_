package com.example.guru24

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivityTermsOfServiceBinding

class ActivityTermsOfService : AppCompatActivity() {
    private var mBinding: ActivityTermsOfServiceBinding? = null
    private val binding get() = mBinding!!

    private var isServiceChecked = false
    private var isPrivacyChecked = false
    private var isAllChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityTermsOfServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // "전체 동의하기" 클릭 시 다른 체크박스 상태 동기화
        binding.checkboxAll.setOnClickListener {
            isAllChecked = !isAllChecked
            updateAllCheckboxImage()
            isServiceChecked = isAllChecked
            isPrivacyChecked = isAllChecked
            updateServiceCheckboxImage()
            updatePrivacyCheckboxImage()
        }

        // "서비스 이용약관" 클릭 시 상태 변경
        binding.checkboxService.setOnClickListener {
            isServiceChecked = !isServiceChecked
            updateServiceCheckboxImage()
            updateAllCheckboxImage()
        }

        // "개인정보 처리방침" 클릭 시 상태 변경
        binding.checkboxPrivacy.setOnClickListener {
            isPrivacyChecked = !isPrivacyChecked
            updatePrivacyCheckboxImage()
            updateAllCheckboxImage()
        }

        // "확인" 버튼 클릭 시 모든 체크박스가 선택되었는지 확인
        binding.ButtonCheck.setOnClickListener {
            if (isServiceChecked && isPrivacyChecked) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "모든 필수 항목에 동의해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 각 체크박스를 클릭했을 때 해당 페이지로 이동
        binding.checkboxService.setOnClickListener {
            if (isServiceChecked) {
                val intent = Intent(this, ServTermsActivity::class.java)
                startActivity(intent)
            }
        }

        binding.checkboxPrivacy.setOnClickListener {
            if (isPrivacyChecked) {
                val intent = Intent(this, PrivTermsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun updateServiceCheckboxImage() {
        if (isServiceChecked) {
            binding.checkboxService.setImageResource(R.drawable.check_small1) // 체크된 상태 이미지
        } else {
            binding.checkboxService.setImageResource(R.drawable.check_small) // 체크되지 않은 상태 이미지
        }
    }

    private fun updatePrivacyCheckboxImage() {
        if (isPrivacyChecked) {
            binding.checkboxPrivacy.setImageResource(R.drawable.check_small1) // 체크된 상태 이미지
        } else {
            binding.checkboxPrivacy.setImageResource(R.drawable.check_small) // 체크되지 않은 상태 이미지
        }
    }

    private fun updateAllCheckboxImage() {
        if (isAllChecked) {
            binding.checkboxAll.setImageResource(R.drawable.check_circle1) // 체크된 상태 이미지
        } else {
            binding.checkboxAll.setImageResource(R.drawable.check_circle) // 체크되지 않은 상태 이미지
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }
}
