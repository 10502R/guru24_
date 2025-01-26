package com.example.guru24

import android.util.Log
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles

class PinManager(private val kakaoMap: KakaoMap) {

    private val labelManager = kakaoMap.labelManager

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

    fun addPin(latitude: Double, longitude: Double, iconResId: Int) {
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
            Log.d("PinManager", "Added pin at Latitude=$latitude, Longitude=$longitude")
        } else {
            Log.e("PinManager", "Failed to add label at Latitude=$latitude, Longitude=$longitude")
        }
    }

    fun removeAllPins() {
        labelManager?.removeAllLabelLayer()
        Log.d("PinManager", "All pins removed from map")
    }
}
