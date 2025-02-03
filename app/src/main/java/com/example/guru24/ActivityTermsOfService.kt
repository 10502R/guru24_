package com.example.guru24

import android.content.Intent
import android.os.Bundle
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

        // 상태 복원
        if (savedInstanceState != null) {
            isServiceChecked = savedInstanceState.getBoolean("isServiceChecked", false)
            isPrivacyChecked = savedInstanceState.getBoolean("isPrivacyChecked", false)
            isAllChecked = isServiceChecked && isPrivacyChecked
        }

        updateServiceCheckboxImage()
        updatePrivacyCheckboxImage()
        updateAllCheckboxImage()

        // "전체 동의하기" 클릭 시 상태 동기화
        binding.checkboxAll.setOnClickListener {
            isAllChecked = !isAllChecked  // 현재 상태를 반전
            isServiceChecked = isAllChecked
            isPrivacyChecked = isAllChecked

            // 모든 체크박스의 UI 갱신
            updateServiceCheckboxImage()
            updatePrivacyCheckboxImage()
            updateAllCheckboxImage()
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
            if (isAllChecked) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "모든 필수 항목에 동의해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 각 체크박스의 '>' 클릭했을 때 해당 페이지로 이동
        binding.textViewServiceDetails.setOnClickListener {
            val intent = Intent(this, ServTermsActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SERVICE_TERMS)
        }

        binding.textViewPrivacyDetails.setOnClickListener {
            val intent = Intent(this, PrivTermsActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_PRIVACY_TERMS)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            // 체크 상태 복원
            isServiceChecked = data?.getBooleanExtra("isServiceChecked", isServiceChecked) ?: isServiceChecked
            isPrivacyChecked = data?.getBooleanExtra("isPrivacyChecked", isPrivacyChecked) ?: isPrivacyChecked
            isAllChecked = isServiceChecked && isPrivacyChecked

            updateServiceCheckboxImage()
            updatePrivacyCheckboxImage()
            updateAllCheckboxImage()
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
        if (isServiceChecked && isPrivacyChecked) {
            isAllChecked = true
            binding.checkboxAll.setImageResource(R.drawable.check_circle1) // 체크된 상태
        } else {
            isAllChecked = false
            binding.checkboxAll.setImageResource(R.drawable.check_circle) // 체크되지 않은 상태
        }
    }

    // 상태 저장
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isServiceChecked", isServiceChecked)
        outState.putBoolean("isPrivacyChecked", isPrivacyChecked)
        outState.putBoolean("isAllChecked", isAllChecked)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

    companion object {
        private const val REQUEST_CODE_SERVICE_TERMS = 1
        private const val REQUEST_CODE_PRIVACY_TERMS = 2
    }
}
