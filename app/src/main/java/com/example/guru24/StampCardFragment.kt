package com.example.guru24

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.example.guru24.databinding.FragmentStampCardBinding

class StampCardFragment : Fragment() {

    // 각 투어의 아코디언 상태를 저장할 변수
    private val isExpandedMap = mutableMapOf<Int, Boolean>()
    private var _binding: FragmentStampCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStampCardBinding.inflate(inflater, container, false)
        val view = binding.root

        // 각 투어에 대한 아코디언 설정
        setupAccordion(binding.rjosjx78edp9, binding.accordionTteokbokki) // 떡볶이 투어
        setupAccordion(binding.rodre89kfxgs, binding.accordionGonggang) // 공강 투어
        setupAccordion(binding.rxvpmlbtlpke, binding.accordionStudy) // 벼락치기 투어
        setupAccordion(binding.r1k8o9swwn23, binding.accordionCu) // 편의점 투어
        setupAccordion(binding.r9swpmxvmgf, binding.accordionCoffee) // 카페 투어

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // 아코디언 설정 함수
    private fun setupAccordion(stampDownImage: ImageView, accordionLayout: LinearLayout) {
        // 초기 상태 설정
        isExpandedMap[stampDownImage.id] = false

        // 클릭 리스너 설정
        stampDownImage.setOnClickListener {
            toggleAccordion(stampDownImage, accordionLayout, stampDownImage.id)
        }
    }

    // 아코디언 토글 함수
    private fun toggleAccordion(stampImage: ImageView, accordionLayout: LinearLayout, stampDownId: Int) {
        val isExpanded = isExpandedMap[stampDownId] ?: false

        if (isExpanded) {
            // 아코디언 접기
            stampImage.setImageResource(R.drawable.stamp_down)
            accordionLayout.visibility = View.GONE
        } else {
            // 아코디언 펼치기
            stampImage.setImageResource(R.drawable.stamp_up)
            accordionLayout.visibility = View.VISIBLE
        }

        // 상태 업데이트
        isExpandedMap[stampDownId] = !isExpanded
    }

    // QR 코드 스캔 결과 처리 함수
    fun onStampAcquired(scanResult: String) {
        val stampImageView = when (scanResult) {
            "https://m.site.naver.com/1BpDN" -> binding.stamp11
            "https://m.site.naver.com/1BpEy" -> binding.stamp12
            "https://m.site.naver.com/1BpEL" -> binding.stamp13
            "https://m.site.naver.com/1BpEU" -> binding.stamp14
            "gonggang" -> binding.rodre89kfxgs
            "study" -> binding.rxvpmlbtlpke
            "cu" -> binding.r1k8o9swwn23
            "coffee" -> binding.r9swpmxvmgf
            else -> null
        }

        if (stampImageView != null) {
            if (stampImageView.drawable.constantState == ResourcesCompat.getDrawable(resources, R.drawable.stamp_swuri_color, null)?.constantState) {
                // 이미 획득한 스탬프 -> 실패 팝업
                showStampPopup(false)
            } else {
                // 새로운 스탬프 획득 -> 성공 팝업 및 이미지 업데이트
                updateStamp(stampImageView, R.drawable.stamp_swuri_color)
                showStampPopup(true)
            }
        } else {
            // 정의되지 않은 QR 코드 -> 실패 팝업
            showStampPopup(false)
        }

        // 모든 스탬프를 찍은 경우
        if (checkAllStampsAcquired()) {
            showBadgeAcquiredPopup()
        }
    }

    // 스탬프 획득 팝업 표시 함수
    private fun showStampPopup(isSuccess: Boolean) {
        val activityClass = if (isSuccess) {
            StampSuccessActivity::class.java
        } else {
            StampFailActivity::class.java
        }
        val intent = Intent(requireContext(), activityClass)
        startActivity(intent)
    }

    // 스탬프 업데이트 함수
    private fun updateStamp(stampImage: ImageView?, stampResourceId: Int) {
        // 이미지가 없거나, 이미 획득한 스탬프인 경우에는 업데이트하지 않음
        if (stampImage?.drawable?.constantState !=
            ResourcesCompat.getDrawable(resources, R.drawable.stamp_swuri_color, null)?.constantState) {
            stampImage?.setImageResource(stampResourceId)
        }
    }

    // 모든 스탬프 획득 여부 확인 함수
    private fun checkAllStampsAcquired(): Boolean {
        val stampImages = listOf(
            binding.rjosjx78edp9,
            binding.rodre89kfxgs,
            binding.rxvpmlbtlpke,
            binding.r1k8o9swwn23,
            binding.r9swpmxvmgf
        )
        return stampImages.all { stampImage ->
            stampImage.drawable.constantState ==
                    ResourcesCompat.getDrawable(resources, R.drawable.stamp_swuri_color, null)?.constantState
        }
    }

    // 뱃지 획득 팝업 표시 함수
    private fun showBadgeAcquiredPopup() {
        AlertDialog.Builder(requireContext())
            .setTitle("축하합니다")
            .setMessage("뱃지를 획득했어요!")
            .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}

