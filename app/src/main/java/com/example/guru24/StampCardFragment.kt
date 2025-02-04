package com.example.guru24

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.guru24.databinding.FragmentStampCardBinding

class StampCardFragment : Fragment() {

    private val isExpandedMap = mutableMapOf<Int, Boolean>()
    private var _binding: FragmentStampCardBinding? = null
    private val binding get() = _binding!!

    private val acquiredStamps = mutableSetOf<String>() // 획득한 스탬프를 저장하는 Set

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStampCardBinding.inflate(inflater, container, false)
        val view = binding.root

        // 스탬프 상태 불러오기
        loadStampState()

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

    // 스탬프 상태 불러오기
    private fun loadStampState() {
        val sharedPref = requireContext().getSharedPreferences("stamp_state", Context.MODE_PRIVATE)
        val stamps = sharedPref.getStringSet("acquired_stamps", mutableSetOf()) ?: mutableSetOf()
        acquiredStamps.addAll(stamps)

        // 불러온 스탬프 상태를 UI에 반영
        updateStampUI()
    }

    // 스탬프 상태 저장하기
    private fun saveStampState() {
        val sharedPref = requireContext().getSharedPreferences("stamp_state", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putStringSet("acquired_stamps", acquiredStamps)
            apply()
        }
    }

    // 스탬프 상태를 UI에 반영
    private fun updateStampUI() {
        for (stamp in acquiredStamps) {
            val stampImageView = when (stamp) {
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
            stampImageView?.setImageResource(R.drawable.stamp_swuri_color)
        }

        // 투어 완료 여부 확인 및 체크 이미지 업데이트
        updateTourCheckImages()
    }

    // 투어 완료 여부 확인 및 체크 이미지 업데이트
    private fun updateTourCheckImages() {
        val sharedPref = requireContext().getSharedPreferences("stamp_state", Context.MODE_PRIVATE)
        val stamps = sharedPref.getStringSet("acquired_stamps", mutableSetOf()) ?: mutableSetOf()

        // 떡볶이 투어 완료 여부 확인
        if (stamps.containsAll(listOf(
                "https://m.site.naver.com/1BpDN",
                "https://m.site.naver.com/1BpEy",
                "https://m.site.naver.com/1BpEL",
                "https://m.site.naver.com/1BpEU"
            ))) {
            binding.rs0ta7414ckc.setImageResource(R.drawable.stamp_ckeck_black)
            // 뱃지 상태 저장
            val badgePref = requireContext().getSharedPreferences("badge_status", Context.MODE_PRIVATE)
            with(badgePref.edit()) {
                putBoolean("tteokbokki", true)
                apply()
            }
        }

        // 공강 투어 완료 여부 확인
        if (stamps.containsAll(listOf(
                "gonggang"
            ))) {
            binding.rodre89kfxgs.setImageResource(R.drawable.stamp_ckeck_black)
            // 뱃지 상태 저장
            val badgePref = requireContext().getSharedPreferences("badge_status", Context.MODE_PRIVATE)
            with(badgePref.edit()) {
                putBoolean("gonggang", true)
                apply()
            }
        }

        // 벼락치기 투어 완료 여부 확인
        if (stamps.containsAll(listOf(
                "study"
            ))) {
            binding.rxvpmlbtlpke.setImageResource(R.drawable.stamp_ckeck_black)
            // 뱃지 상태 저장
            val badgePref = requireContext().getSharedPreferences("badge_status", Context.MODE_PRIVATE)
            with(badgePref.edit()) {
                putBoolean("study", true)
                apply()
            }
        }

        // 편의점 투어 완료 여부 확인
        if (stamps.containsAll(listOf(
                "cu"
            ))) {
            binding.r1k8o9swwn23.setImageResource(R.drawable.stamp_ckeck_black)
            // 뱃지 상태 저장
            val badgePref = requireContext().getSharedPreferences("badge_status", Context.MODE_PRIVATE)
            with(badgePref.edit()) {
                putBoolean("cu", true)
                apply()
            }
        }

        // 카페 투어 완료 여부 확인
        if (stamps.containsAll(listOf(
                "coffee"
            ))) {
            binding.r9swpmxvmgf.setImageResource(R.drawable.stamp_ckeck_black)
            // 뱃지 상태 저장
            val badgePref = requireContext().getSharedPreferences("badge_status", Context.MODE_PRIVATE)
            with(badgePref.edit()) {
                putBoolean("coffee", true)
                apply()
            }
        }

        // 다른 투어에 대해서도 동일한 로직 추가
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
            if (acquiredStamps.contains(scanResult)) {
                // 이미 획득한 스탬프 -> 실패 팝업
                showStampPopup(false)
            } else {
                // 새로운 스탬프 획득 -> 성공 팝업 및 이미지 업데이트
                acquiredStamps.add(scanResult)
                updateStamp(stampImageView, R.drawable.stamp_swuri_color)
                // 스탬프 획득 후 4개를 다 모았는지 확인
                if (isTourComplete(scanResult)) {
                    showBadgeActivity(scanResult)
                    showBadgeAcquiredPopup() // 모든 스탬프를 획득한 후 뱃지 획득 팝업 표시
                } else {
                    showStampPopup(true)
                }
                // 스탬프 상태 저장하기
                saveStampState()
            }
        } else {
            // 정의되지 않은 QR 코드 -> 실패 팝업
            showStampPopup(false)
        }
    }

    // 투어 완료 여부 확인 함수
    private fun isTourComplete(scanResult: String): Boolean {
        return when (scanResult) {
            "https://m.site.naver.com/1BpDN", "https://m.site.naver.com/1BpEy",
            "https://m.site.naver.com/1BpEL", "https://m.site.naver.com/1BpEU" -> listOf(
                binding.stamp11,
                binding.stamp12,
                binding.stamp13,
                binding.stamp14
            ).all { stampImage ->
                stampImage.drawable.constantState == ResourcesCompat.getDrawable(resources, R.drawable.stamp_swuri_color, null)?.constantState
            }
            "gonggang" -> true
            "study" -> true
            "cu" -> true
            "coffee" -> true
            // 추가 투어에 대한 로직
            else -> false
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

    // 뱃지 획득 화면 표시 함수
    private fun showBadgeActivity(scanResult: String) {
        val intent = Intent(requireContext(), BadgeActivity::class.java)
        intent.putExtra("tourType", getTourType(scanResult))
        startActivity(intent)
    }

    // 스탬프 업데이트 함수
    private fun updateStamp(stampImage: ImageView?, stampResourceId: Int) {
        stampImage?.setImageResource(stampResourceId)
    }

    // 투어 타입 얻기 함수
    private fun getTourType(scanResult: String): String {
        return when (scanResult) {
            "https://m.site.naver.com/1BpDN", "https://m.site.naver.com/1BpEy",
            "https://m.site.naver.com/1BpEL", "https://m.site.naver.com/1BpEU" -> "tteokbokki"
            "gonggang" -> "gonggang"
            "study" -> "study"
            "cu" -> "cu"
            "coffee" -> "coffee"
            // 추가 투어에 대한 로직
            else -> ""
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
