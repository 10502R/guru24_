package com.example.guru24

import android.util.Log
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles

class PinManager(private val kakaoMap: KakaoMap) {

    private val labelManager = kakaoMap.labelManager
    private val pinList = mutableListOf<PinData>() // 마커 데이터를 관리
    private val pinsByGroup = mutableMapOf<String, MutableList<PinData>>() // 그룹별 핀 관리

    data class PinData(
        val latitude: Double,
        val longitude: Double,
        val group: String,
        val iconResId: Int
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

    fun addPin(latitude: Double, longitude: Double, iconResId: Int, group: String) {
        val styles = createLabelStyle(iconResId) ?: return
        val options = LabelOptions.from(LatLng.from(latitude, longitude)).setStyles(styles)

        val layer = labelManager?.layer
        if (layer == null) {
            Log.e("PinManager", "LabelLayer is null")
            return
        }

        val label = layer.addLabel(options)
        if (label != null) {
            label.show()
            val pin = PinData(latitude, longitude, group, iconResId)
            pinList.add(pin)
            pinsByGroup.getOrPut(group) { mutableListOf() }.add(pin) // 그룹별로 핀 추가
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


