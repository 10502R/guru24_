package com.example.guru24

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

    private var selectedImageView: ImageView? = null

    data class PinData(
        val latitude: Double,
        val longitude: Double,
        val group: String,
        val iconResId: Int
    )

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

        // ImageView 클릭 리스너 설정
        binding.image1.setOnClickListener { handleImageClick(binding.image1, R.drawable.ic_tour_tteokbokki, R.drawable.ic_tour_tteokbokki2) }
        binding.image2.setOnClickListener { handleImageClick(binding.image2, R.drawable.ic_tour_air, R.drawable.ic_tour_air2) }
        binding.image3.setOnClickListener { handleImageClick(binding.image3, R.drawable.ic_tour_zz, R.drawable.ic_tour_zz2) }
        binding.image4.setOnClickListener { handleImageClick(binding.image4, R.drawable.ic_tour_store, R.drawable.ic_tour_store) }
        binding.image5.setOnClickListener { handleImageClick(binding.image5, R.drawable.ic_tour_zz2, R.drawable.ic_tour_cafe2) }

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
            18,     // 줌 레벨 (15으로 설정하면 대학 캠퍼스를 자세히 볼 수 있음)
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
        // 동아리
        pinManager.addPin(37.62866684063515, 127.09070105680749, R.drawable.icon_club, "동아리", "동아리"); // 동아리

        // 은행
        pinManager.addPin(37.62869873773617, 127.09022813360964, R.drawable.icon_bank, "은행", "우리은행"); // 우리은행

        // 음식점
        pinManager.addPin(37.62885613457392, 127.09058800230274, R.drawable.icon_store, "음식점", "츄츄바앤츄밥"); // 츄츄바앤츄밥
        pinManager.addPin(37.62659059850936, 127.09291317936346, R.drawable.icon_store, "음식점", "교직원 식당"); // 교직원 식당
        pinManager.addPin(37.626347381443864, 127.0928505722139, R.drawable.icon_store, "음식점", "감탄떡볶이"); // 감탄떡볶이
        pinManager.addPin(37.62622118326326, 127.09292687959817, R.drawable.icon_store, "음식점", "오니기리와이규동"); // 오니기리와이규동
        pinManager.addPin(37.62885625188801, 127.09043506831497, R.drawable.icon_store, "음식점", "버거ING"); // 버거ING
        pinManager.addPin(37.62880664558206, 127.09050297888797, R.drawable.icon_store, "음식점", "더큰도시락"); // 더큰도시락
        pinManager.addPin(37.62857355250948, 127.08897336072911, R.drawable.icon_store, "음식점", "기숙사식당"); // 기숙사식당
        pinManager.addPin(37.628687040925726, 127.09079454088958, R.drawable.icon_food, "음식점", "구시아"); // 구시아

        // 주차/셔틀
        pinManager.addPin(37.62712879896861, 127.09308943627939, R.drawable.icon_bus, "주차/셔틀", "셔틀버스"); // 셔틀버스

        // 카페/베이커리
        pinManager.addPin(37.62858586289631, 127.09055652113533, R.drawable.icon_store, "카페/베이커리", "퀴즈노스 서울여대점"); // 퀴즈노스 서울여대점
        pinManager.addPin(37.62611302036721, 127.09298338513028, R.drawable.icon_store, "카페/베이커리", "뚜레쥬르"); // 뚜레쥬르
        pinManager.addPin(37.626029518338456, 127.09318718556041, R.drawable.icon_store, "카페/베이커리", "카페ING"); // 카페ING
        pinManager.addPin(37.62851378841688, 127.09055076948754, R.drawable.icon_store, "카페/베이커리", "비틀주스"); // 비틀주스
        pinManager.addPin(37.62879745737408, 127.09073520087216, R.drawable.icon_store, "카페/베이커리", "컴포즈커피"); // 컴포즈커피
        pinManager.addPin(37.62881539645281, 127.09084001082321, R.drawable.icon_store, "카페/베이커리", "에땅"); // 에땅
        pinManager.addPin(37.627626246037536, 127.09064315094663, R.drawable.icon_store, "카페/베이커리", "카페 팬도로시"); // 카페 팬도로시

        // 편의시설
        pinManager.addPin(37.6260586839787, 127.09333448635181, R.drawable.icon_store, "편의시설", "카피웍스 복사실"); // 카피웍스 복사실
        pinManager.addPin(37.62884700720883, 127.09074092533572, R.drawable.icon_store, "편의시설", "누리스토어"); // 누리스토어
        pinManager.addPin(37.62852947084557, 127.09066124045721, R.drawable.icon_store, "편의시설", "SWEET U"); // SWEET U
        pinManager.addPin(37.62611959005892, 127.09322128212314, R.drawable.icon_store, "편의시설", "구내서점"); // 구내서점
        pinManager.addPin(37.62876155285828, 127.09055956640816, R.drawable.icon_store, "편의시설", "보건실"); // 보건실
        pinManager.addPin(37.62846158944593, 127.09106048389674, R.drawable.icon_store, "편의시설", "멀티플렉스존"); // 멀티플렉스존
        pinManager.addPin(37.62846592149489, 127.09128422497902, R.drawable.icon_store, "편의시설", "미디어룸"); // 미디어룸

        // 편의점
        pinManager.addPin(37.62647582535639, 127.09278276343268, R.drawable.icon_store, "편의점", "CU 편의점"); // CU 편의점
        pinManager.addPin(37.62883702662517, 127.0890586381962, R.drawable.icon_store, "편의점", "세븐일레븐 편의점"); // 세븐일레븐 편의점
        pinManager.addPin(37.62833893940938, 127.092368761627, R.drawable.icon_store, "편의점", "GS25 편의점"); // GS25 편의점

        // 학과사무실
        pinManager.addPin(37.62925489443627, 127.0904921939158, R.drawable.icon_store, "학과사무실", "정보보호학과"); // 정보보호학과
        pinManager.addPin(37.62822388842327, 127.09259235431496, R.drawable.icon_store, "학과사무실", "교육심리학과"); // 교육심리학과

        // 학습공간
        pinManager.addPin(37.62855601126201, 127.09129566357015, R.drawable.icon_store, "학습공간", "리딩라운지"); // 리딩라운지
        pinManager.addPin(37.62932261153444, 127.09030535535916, R.drawable.icon_store, "학습공간", "러닝커먼스"); // 러닝커먼스
        pinManager.addPin(37.628228511041456, 127.09244225929751, R.drawable.icon_store, "학습공간", "스터디룸"); // 스터디룸
        pinManager.addPin(37.628443477882534, 127.09117940990961, R.drawable.icon_store, "학습공간", "세미나실"); // 세미나실


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

    private fun handleImageClick(imageView: ImageView, defaultImage: Int, toggledImage: Int) {
        // 이전에 선택된 ImageView가 있으면 원래 이미지로 변경
        selectedImageView?.setImageResource(
            when (selectedImageView) {
                binding.image1 -> R.drawable.ic_tour_tteokbokki
                binding.image2 -> R.drawable.ic_tour_air
                binding.image3 -> R.drawable.ic_tour_zz
                binding.image4 -> R.drawable.ic_tour_store
                binding.image5 -> R.drawable.ic_tour_zz2
                else -> 0
            }
        )

        // 현재 선택된 ImageView를 변경
        selectedImageView = if (selectedImageView == imageView) {
            imageView.setImageResource(defaultImage)
            null
        } else {
            imageView.setImageResource(toggledImage)
            imageView
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
