package com.example.guru24

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.guru24.databinding.FragmentMapBinding
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.KakaoMapSdk
import com.kakao.vectormap.camera.CameraPosition
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelStyle

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapView: View // 초기화 전에 lateinit 사용
    private lateinit var kakaoMap: KakaoMap // KakaoMap 변수 추가

    private lateinit var pinManager: PinManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        // 지도 표시 함수 호출
        showMapView()

        return binding.root
    }

    private fun showMapView() {
        // 바인딩된 mapView 초기화
        mapView = binding.mapView

        // KakaoMapSDK 초기화
        KakaoMapSdk.init(requireContext(), BuildConfig.KAKAO_MAP_KEY)

        binding.mapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                // 지도 API가 정상적으로 종료될 때 호출
                Log.d("KakaoMap", "onMapDestroy")
            }

            override fun onMapError(p0: Exception?) {
                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출
                Log.e("KakaoMap", "onMapError")
            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaomap: KakaoMap) {
                // 정상적으로 인증이 완료되었을 때 호출
                Log.d("KakaoMap", "onMapReady")
                // 지도 준비 완료 후 처리
                kakaoMap = kakaomap

                // PinManager 초기화
                pinManager = PinManager(kakaoMap)

                // 지도 위치 변경
                setSeoulWomenUniversityLocation()

                // 핀
                addPins()
            }
        })
    }

    private fun setSeoulWomenUniversityLocation() {
        // 서울여자대학교 좌표
        val latitude = 37.62613777291973
        val longitude = 127.09301456807785

        // 카메라 위치 설정
        val cameraPosition = CameraPosition.from(
            latitude,
            longitude,
            7,     // 줌 레벨 (15으로 설정하면 대학 캠퍼스를 자세히 볼 수 있음)
            0.0,    // 기울기
            0.0,    // 회전 각도
            0.0     // 고도
        )

        // 카메라 이동
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        kakaoMap.moveCamera(cameraUpdate)
    }

    private fun addPins() {
        // PinManager가 null인지 확인
        if (!::pinManager.isInitialized) {
            Log.e("KakaoMap", "PinManager is not initialized")
            return
        }

        // 퀴즈노스 서울여대점
        pinManager.addPin(
            latitude = 37.62848695743887,
            longitude = 127.09029159983092,
            iconResId = R.drawable.fork_s
        )

        // 츄츄바앤츄밥
        pinManager.addPin(
            latitude = 37.628747057327836,
            longitude = 127.09036979749189,
            iconResId = R.drawable.fork_s
        )

        // 카페 딕셔너리
        pinManager.addPin(
            latitude = 37.62827783313655,
            longitude = 127.09129390726292,
            iconResId = R.drawable.fork_s
        )

        // CU 편의점
        pinManager.addPin(
            latitude = 37.62645655460085,
            longitude = 127.0929413327344,
            iconResId = R.drawable.fork_s
        )

        // 뚜레쥬르
        pinManager.addPin(
            latitude = 37.626305537946706,
            longitude = 127.09307000145742,
            iconResId = R.drawable.fork_s
        )

        // 감탄떡볶이
        pinManager.addPin(
            latitude = 37.62650837242462,
            longitude = 127.09292723713305,
            iconResId = R.drawable.fork_s
        )

        // 카피웍스 복사실
        pinManager.addPin(
            latitude = 37.62592368718219,
            longitude = 127.09314315740546,
            iconResId = R.drawable.fork_s
        )

        // 카페ING
        pinManager.addPin(
            latitude = 37.62626159367428,
            longitude = 127.0930968508079,
            iconResId = R.drawable.fork_s
        )

        // SWEET U
        pinManager.addPin(
            latitude = 37.6285148287459,
            longitude = 127.09066263872195,
            iconResId = R.drawable.fork_s
        )

        // 버거ING
        pinManager.addPin(
            latitude = 37.62870745134154,
            longitude = 127.0906147268686,
            iconResId = R.drawable.fork_s
        )

        // 세븐일레븐 편의점
        pinManager.addPin(
            latitude = 37.628731155504575,
            longitude = 127.08906559214024,
            iconResId = R.drawable.fork_s
        )

        // 구내서점
        pinManager.addPin(
            latitude = 37.62602946687954,
            longitude = 127.093252321639,
            iconResId = R.drawable.fork_s
        )

        // 카페 팬도로시
        pinManager.addPin(
            latitude = 37.62761721768795,
            longitude = 127.09066721254787,
            iconResId = R.drawable.fork_s
        )

        // GS25 편의점
        pinManager.addPin(
            latitude = 37.627877084582934,
            longitude = 127.09249563349782,
            iconResId = R.drawable.fork_s
        )

        // 설화방
        pinManager.addPin(
            latitude = 37.62897908431194,
            longitude = 127.09179888204653,
            iconResId = R.drawable.fork_s
        )

        // 구시아
        pinManager.addPin(
            latitude = 37.62850356751727,
            longitude = 127.09066120899283,
            iconResId = R.drawable.fork_s
        )

        // 우리은행
        pinManager.addPin(
            latitude = 37.628537642198594,
            longitude = 127.09028599694307,
            iconResId = R.drawable.fork_s
        )

        // 셔틀버스
        pinManager.addPin(
            latitude = 37.62725719610109,
            longitude = 127.09308110025269,
            iconResId = R.drawable.fork_s
        )

        // SWU FC
        pinManager.addPin(
            latitude = 37.62866113323955,
            longitude = 127.09080017358565,
            iconResId = R.drawable.fork_s
        )

        // 정보보호학과
        pinManager.addPin(
            latitude = 37.62928875302238,
            longitude = 127.09039877467993,
            iconResId = R.drawable.fork_s
        )

        // 교육심리학과
        pinManager.addPin(
            latitude = 37.62822280107313,
            longitude = 127.09254279140082,
            iconResId = R.drawable.fork_s
        )

    }


    companion object {
        fun setPinStyle(isSelected: Boolean): LabelStyle {
            if (isSelected) {
                LabelStyle.from(
                    R.drawable.fork_s
                ).setTextStyles(20, R.color.black)
            }
            return LabelStyle.from(
                R.drawable.check_circle
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
