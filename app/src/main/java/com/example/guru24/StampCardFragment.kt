package com.example.guru24

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class StampCardFragment : Fragment() {

    private lateinit var stampDownImage: ImageView
    private lateinit var stampCheckImage: ImageView
    private lateinit var stampText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stamp_card, container, false)

        stampDownImage = view.findViewById(R.id.rjosjx78edp9)
        stampCheckImage = view.findViewById(R.id.rs0ta7414ckc)
        stampText = view.findViewById(R.id.ru7h5k5str)

        stampDownImage.setOnClickListener {
            toggleAccordion()
        }

        return view
    }

    private fun toggleAccordion() {
        if (stampDownImage.drawable.constantState == resources.getDrawable(R.drawable.stamp_down).constantState) {
            stampDownImage.setImageResource(R.drawable.stamp_up)
            // 여기에 아코디언 펼친 후의 추가 로직 구현
        } else {
            stampDownImage.setImageResource(R.drawable.stamp_down)
            // 여기에 아코디언 접힌 후의 추가 로직 구현
        }
    }

    // 스탬프 획득 로직
    fun onStampAcquired() {
        // 스탬프 획득 시 이미지 변경
        stampCheckImage.setImageResource(R.drawable.stamp_ckeck_black)
        // 스탬프 획득 팝업 표시
        showStampPopup()
    }

    private fun showStampPopup() {
        // 팝업 표시 로직 구현
    }

    // 뱃지 획득 로직
    fun onBadgeAcquired() {
        // 뱃지 획득 시 이미지 변경 및 텍스트 업데이트
        stampCheckImage.setImageResource(R.drawable.stamp_ckeck_black)
        stampText.text = "1"
        // 뱃지 획득 팝업 표시
        showBadgePopup()
    }

    private fun showBadgePopup() {
        // 뱃지 획득 팝업 표시 로직 구현
    }
}