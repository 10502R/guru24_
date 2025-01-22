package com.example.guru24

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guru24.databinding.ActivityTermsOfServiceBinding

class ActivityTermsOfService : AppCompatActivity() {
    private var mBinding: ActivityTermsOfServiceBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityTermsOfServiceBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // "전체 동의하기" 체크박스 클릭 시 다른 체크박스 상태 동기화
        binding.checkboxAll.setOnCheckedChangeListener { _, isChecked ->
            binding.checkboxService.isChecked = isChecked
            binding.checkboxPrivacy.isChecked = isChecked
        }

        // 각 체크박스 상태에 따라 "전체 동의하기" 체크박스 상태 동기화
        binding.checkboxService.setOnCheckedChangeListener { _, _ ->
            binding.checkboxAll.isChecked =
                binding.checkboxService.isChecked && binding.checkboxPrivacy.isChecked
        }

        binding.checkboxPrivacy.setOnCheckedChangeListener { _, _ ->
            binding.checkboxAll.isChecked =
                binding.checkboxService.isChecked && binding.checkboxPrivacy.isChecked
        }

        // "확인" 버튼 클릭 시 모든 체크박스가 선택되었는지 확인
        binding.ButtonCheck.setOnClickListener {
            if (binding.checkboxService.isChecked && binding.checkboxPrivacy.isChecked) {
                val intent = Intent(this, TermsActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "모든 필수 항목에 동의해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 각 체크박스를 클릭했을 때 해당 페이지로 이동
        binding.checkboxService.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val intent = Intent(this, TermsActivity::class.java)
                startActivity(intent)
            }
            binding.checkboxAll.isChecked =
                binding.checkboxService.isChecked && binding.checkboxPrivacy.isChecked
        }

        binding.checkboxPrivacy.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val intent = Intent(this, TermsActivity::class.java)
                startActivity(intent)
            }
            binding.checkboxAll.isChecked =
                binding.checkboxService.isChecked && binding.checkboxPrivacy.isChecked
        }
    }
}