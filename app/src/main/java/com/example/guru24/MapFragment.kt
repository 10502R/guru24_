package com.example.guru24

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.guru24.databinding.FragmentMapBinding
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.KakaoMapSdk
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraPosition
import com.kakao.vectormap.camera.CameraUpdateFactory

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapView: View // 초기화 전에 lateinit 사용
    private lateinit var kakaoMap: KakaoMap // KakaoMap 변수 추가

    private lateinit var pinManager: PinManager

    data class PinData(
        val latitude: Double,
        val longitude: Double,
        val group: String,
        val iconResId: Int
    )



    private val pinList = mutableListOf<PinData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        // 지도 표시 함수 호출
        showMapView()

        // 검색창 클릭 시 SearchActivity로 이동
        binding.searchView.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }
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

                // 초기 맵 핀 설정
                setInitialPins()

                // 버튼
                setupButtonActions()
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
            10,     // 줌 레벨 (15으로 설정하면 대학 캠퍼스를 자세히 볼 수 있음)
            0.0,    // 기울기
            0.0,    // 회전 각도
            0.0     // 고도
        )

        // 카메라 이동
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        kakaoMap.moveCamera(cameraUpdate)
    }
    
    // 처음 맵 핀
    private fun setInitialPins() {
        // PinManager가 null인지 확인
        if (!::pinManager.isInitialized) {
            Log.e("KakaoMap", "PinManager is not initialized")
            return
        }
        // 은행
        pinManager.addPin(37.628537642198594, 127.09028599694307, R.drawable.icon_bank, "은행", "우리은행"); // 우리은행

        // 음식점
        pinManager.addPin(37.628747057327836, 127.09036979749189, R.drawable.icon_rest, "음식점"); // 츄츄바앤츄밥
        pinManager.addPin(37.62650837242462, 127.09292723713305, R.drawable.icon_rest, "음식점"); // 감탄떡볶이
        pinManager.addPin(37.62870745134154, 127.0906147268686, R.drawable.icon_rest, "음식점"); // 버거ING
        pinManager.addPin(37.62850356751727, 127.09066120899283, R.drawable.icon_rest, "음식점"); // 구시아

        // 카페/베이커리
        pinManager.addPin(37.62848695743887, 127.09029159983092, R.drawable.icon_cafe, "카페/베이커리"); // 퀴즈노스 서울여대점
        pinManager.addPin(37.62827783313655, 127.09129390726292, R.drawable.icon_cafe, "카페/베이커리"); // 카페 딕셔너리
        pinManager.addPin(37.626305537946706, 127.09307000145742, R.drawable.icon_cafe, "카페/베이커리"); // 뚜레쥬르
        pinManager.addPin(37.62626159367428, 127.0930968508079, R.drawable.icon_cafe, "카페/베이커리"); // 카페ING
        pinManager.addPin(37.62761721768795, 127.09066721254787, R.drawable.icon_cafe, "카페/베이커리"); // 카페 팬도로시

        // 편의점
        pinManager.addPin(37.62645655460085, 127.0929413327344, R.drawable.icon_market, "편의점"); // CU 편의점
        pinManager.addPin(37.628731155504575, 127.08906559214024, R.drawable.icon_market, "편의점"); // 세븐일레븐 편의점
        pinManager.addPin(37.627877084582934, 127.09249563349782, R.drawable.icon_market, "편의점"); // GS25 편의점
        pinManager.addPin(37.62884475692359, 127.09073809048682, R.drawable.icon_market, "편의점", "누리스토어"); // 누리스토어

        // 편의시설
        pinManager.addPin(37.62592368718219, 127.09314315740546, R.drawable.icon_store, "편의시설", "카피웍스 복사실"); // 카피웍스 복사실
        pinManager.addPin(37.6285148287459, 127.09066263872195, R.drawable.icon_store, "편의시설", "SWEET U"); // SWEET U
        pinManager.addPin(37.62602946687954, 127.093252321639, R.drawable.icon_store, "편의시설", "구내서점"); // 구내서점
        pinManager.addPin(37.62897908431194, 127.09179888204653, R.drawable.icon_store, "편의시설", "설화방"); // 설화방

        // 주차/셔틀
        pinManager.addPin(37.62725719610109, 127.09308110025269, R.drawable.icon_bus, "주차/셔틀", "셔틀버스"); // 셔틀버스

        // 동아리
        pinManager.addPin(37.62866113323955, 127.09080017358565, R.drawable.icon_club, "동아리", "동아리"); // 동아리방 모음

        // 학과사무실
        pinManager.addPin(37.62928875302238, 127.09039877467993, R.drawable.icon_office, "학과사무실" ,"정보보호학과사무실"); // 정보보호학과
        pinManager.addPin(37.62822280107313, 127.09254279140082, R.drawable.icon_office, "학과사무실", "교육심리학과사무실"); // 교육심리학과

    }

    private fun setupButtonActions() {
        binding.layoutrestaurant.setOnClickListener {
            if (!::pinManager.isInitialized) {
                Log.e("MapFragment", "PinManager is not initialized")
                return@setOnClickListener
            }

            // 모든 핀 삭제
            pinManager.removeAllPins()

            // 음식점
            pinManager.addPin(37.628747057327836, 127.09036979749189, R.drawable.icon_rest, "음식점"); // 츄츄바앤츄밥
            pinManager.addPin(37.62650837242462, 127.09292723713305, R.drawable.icon_rest, "음식점"); // 감탄떡볶이
            pinManager.addPin(37.62870745134154, 127.0906147268686, R.drawable.icon_rest, "음식점"); // 버거ING
            pinManager.addPin(37.62850356751727, 127.09066120899283, R.drawable.icon_rest, "음식점"); // 구시아
            Log.d("MapFragment", "Added pin for food_market")
        }

        binding.layoutstore.setOnClickListener {
            if (!::pinManager.isInitialized) {
                Log.e("MapFragment", "PinManager is not initialized")
                return@setOnClickListener
            }
            // 모든 핀 삭제
            pinManager.removeAllPins()

            // 편의점
            pinManager.addPin(37.62645655460085, 127.0929413327344, R.drawable.icon_market, "편의점"); // CU 편의점
            pinManager.addPin(37.628731155504575, 127.08906559214024, R.drawable.icon_market, "편의점"); // 세븐일레븐 편의점
            pinManager.addPin(37.627877084582934, 127.09249563349782, R.drawable.icon_market, "편의점"); // GS25 편의점
            pinManager.addPin(37.62884475692359, 127.09073809048682, R.drawable.icon_market, "편의점", "누리스토어"); // 누리스토어
        }

        binding.layoutoffice.setOnClickListener {
            if (!::pinManager.isInitialized) {
                Log.e("MapFragment", "PinManager is not initialized")
                return@setOnClickListener
            }
            // 모든 핀 삭제
            pinManager.removeAllPins()

            // 학과사무실
            pinManager.addPin(37.62928875302238, 127.09039877467993, R.drawable.icon_office, "학과사무실" ,"정보보호학과사무실"); // 정보보호학과
            pinManager.addPin(37.62822280107313, 127.09254279140082, R.drawable.icon_office, "학과사무실", "교육심리학과사무실"); // 교육심리학과
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
