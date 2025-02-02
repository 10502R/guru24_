package com.example.guru24

import android.app.Activity
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
import androidx.activity.result.contract.ActivityResultContracts

class StampCardFragment : Fragment() {

    // 각 투어의 아코디언 상태를 저장할 변수
    private val isExpandedMap = mutableMapOf<Int, Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stamp_card, container, false)

        // 각 투어에 대한 아코디언 설정
        setupAccordion(view, R.id.rjosjx78edp9, R.id.accordion_tteokbokki) // 떡볶이 투어
        setupAccordion(view, R.id.rodre89kfxgs, R.id.accordion_gonggang) // 공강 투어
        setupAccordion(view, R.id.rxvpmlbtlpke, R.id.accordion_study) // 벼락치기 투어
        setupAccordion(view, R.id.r1k8o9swwn23, R.id.accordion_cu) // 편의점 투어
        setupAccordion(view, R.id.r9swpmxvmgf, R.id.accordion_coffee) // 카페 투어

        return view
    }

    // 아코디언 설정 함수
    private fun setupAccordion(view: View, stampDownId: Int, accordionLayoutId: Int) {
        val stampDownImage = view.findViewById<ImageView>(stampDownId)
        val accordionLayout = view.findViewById<LinearLayout>(accordionLayoutId)

        // 초기 상태 설정
        isExpandedMap[stampDownId] = false

        // 클릭 리스너 설정
        stampDownImage.setOnClickListener {
            toggleAccordion(stampDownImage, accordionLayout, stampDownId)
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

    // QR 코드 스캔 결과 처리
    private val qrCodeLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val scanResult = result.data?.getStringExtra("SCAN_RESULT")
            if (scanResult != null) {
                onStampAcquired(scanResult) // 스탬프 획득 처리
            }
        }
    }

    // 스탬프 획득 처리 함수
    private fun onStampAcquired(scanResult: String) {
        // scanResult에 따라 스탬프 이미지 업데이트
        when (scanResult) {
            "tteokbokki" -> updateStamp(R.id.rjosjx78edp9, R.drawable.stamp_swuri_color)
            "gonggang" -> updateStamp(R.id.rodre89kfxgs, R.drawable.stamp_usi_color)
            "study" -> updateStamp(R.id.rxvpmlbtlpke, R.drawable.stamp_wendy_color)
            "cu" -> updateStamp(R.id.r1k8o9swwn23, R.drawable.stamp_swuri_color)
            "coffee" -> updateStamp(R.id.r9swpmxvmgf, R.drawable.stamp_usi_color)
        }

        // 스탬프 획득 팝업 표시
        showStampPopup()

        // 모든 스탬프를 찍은 경우
        if (checkAllStampsAcquired()) {
            showBadgeAcquiredPopup()
        }
    }

    // 스탬프 업데이트 함수
    private fun updateStamp(stampId: Int, stampResourceId: Int) {
        val stampImage = requireView().findViewById<ImageView>(stampId)
        stampImage.setImageResource(stampResourceId)
    }

    // 모든 스탬프 획득 여부 확인 함수
    private fun checkAllStampsAcquired(): Boolean {
        val stampIds = listOf(
            R.id.rjosjx78edp9,
            R.id.rodre89kfxgs,
            R.id.rxvpmlbtlpke,
            R.id.r1k8o9swwn23,
            R.id.r9swpmxvmgf
        )
        return stampIds.all { id ->
            val stampImage = requireView().findViewById<ImageView>(id)
            stampImage.drawable.constantState == ResourcesCompat.getDrawable(resources, R.drawable.stamp_swuri_color, null)?.constantState
        }
    }

    // 스탬프 획득 팝업 표시 함수
    private fun showStampPopup() {
        AlertDialog.Builder(requireContext())
            .setTitle("스탬프 획득")
            .setMessage("스탬프를 획득했습니다!")
            .setPositiveButton("확인") { dialog, _ -> dialog.dismiss() }
            .show()
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
