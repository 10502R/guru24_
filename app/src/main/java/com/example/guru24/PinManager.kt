package com.example.guru24

import android.graphics.Color
import android.util.Log
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.label.LabelTextBuilder

class PinManager(private val kakaoMap: KakaoMap) {

    private val labelManager = kakaoMap.labelManager
    private val pinList = mutableListOf<PinData>() // 마커 데이터를 관리
    private val pinsByGroup = mutableMapOf<String, MutableList<PinData>>() // 그룹별 핀 관리

    data class PinData(
        val latitude: Double,
        val longitude: Double,
        val group: String,
        val iconResId: Int,
        val text: String? = null
    )

    init {
        requireNotNull(labelManager) { "LabelManager is null" }
    }

    private fun createLabelStyle(iconResId: Int): LabelStyles? {
        val styles = labelManager?.addLabelStyles(
            LabelStyles.from(LabelStyle.from(iconResId))
        )
        if (styles == null) {
            Log.e("PinManager", "Failed to create LabelStyles for icon: $iconResId")
        }
        return styles
    }

    fun addPin(latitude: Double, longitude: Double, iconResId: Int, group: String, text: String? = null) {
        val layer = kakaoMap?.labelManager?.layer
        if (layer == null) {
            Log.e("PinManager", "LabelLayer is null")
            return
        }

        val labelStyle = LabelStyle.from(iconResId)
            .setAnchorPoint(0.5f, 0.9f)
            .setTextStyles(14, Color.BLACK, 2, Color.WHITE)

        val styles = labelManager?.addLabelStyles(LabelStyles.from(labelStyle)) ?: return

        val latLng = LatLng.from(latitude, longitude)

        val options = LabelOptions.from(latLng)
            .setStyles(styles)

        // 텍스트가 있을 경우 추가
        text?.let {
            val labelText = LabelTextBuilder().setTexts(it)
            options.setTexts(labelText)
        }

        val label = layer.addLabel(options)

        if (label != null) {
            label.show()
            label.invalidate()
            Log.d("PinManager", "Added pin at Latitude=$latitude, Longitude=$longitude with text=$text")

            val pinData = PinData(latitude, longitude, group, iconResId, text)
            pinList.add(pinData)
            pinsByGroup.getOrPut(group) { mutableListOf() }.add(pinData)
        } else {
            Log.e("PinManager", "Failed to add label at Latitude=$latitude, Longitude=$longitude")
        }
    }

    fun removeAllPins() {
        pinList.clear() // 핀 데이터 초기화
        pinsByGroup.clear() // 그룹 데이터 초기화
        labelManager?.removeAllLabelLayer() // 모든 라벨 숨기기
        Log.d("PinManager", "All pins removed from map and list")
    }
}
