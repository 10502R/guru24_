package com.example.guru24

import android.content.Intent
import android.graphics.Color
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
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.camera.CameraAnimation
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.route.RouteLine
import com.kakao.vectormap.route.RouteLineOptions
import com.kakao.vectormap.route.RouteLineSegment
import com.kakao.vectormap.route.RouteLineStyle
import com.kakao.vectormap.route.RouteLineStyles
import com.kakao.vectormap.route.RouteLineStylesSet
import java.util.Arrays


class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapView: View // 초기화 전에 lateinit 사용
    private lateinit var kakaoMap: KakaoMap // KakaoMap 변수 추가

    private lateinit var pinManager: PinManager

    private var selectedImageView: ImageView? = null
    private var currentRouteLine: RouteLine? = null

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
        binding.image1.setOnClickListener {
            handleImageClick(binding.image1, R.drawable.ic_tour_tteokbokki, R.drawable.ic_tour_tteokbokki2)

            if (!::pinManager.isInitialized) {
                return@setOnClickListener
            }

            // 카메라 위치 이동
            setLocation()

            // 모든 핀 삭제 후 새로운 핀 추가
            pinManager.removeAllPins()

            pinManager.addPin(37.62885613457392, 127.09058800230274, R.drawable.icon_food2, "음식점", "츄츄바앤츄밥")
            pinManager.addPin(37.626347381443864, 127.0928505722139, R.drawable.icon_food2, "음식점", "감탄떡볶이")
            pinManager.addPin(37.62880664558206, 127.09050297888797, R.drawable.icon_food2, "음식점", "더큰도시락")
            pinManager.addPin(37.628687040925726, 127.09079454088958, R.drawable.icon_food2, "음식점", "구시아")

            // RouteLineLayer 가져오기
            val layer = kakaoMap.routeLineManager!!.layer

            // 기존 경로 삭제 (remove() 또는 layer.clear() 활용)
            currentRouteLine?.remove()
            layer.removeAll()

            // RouteLineStylesSet 생성하기 (파란색 스타일)
            val stylesSet = RouteLineStylesSet.from(
                "blueStyles",
                RouteLineStyles.from(RouteLineStyle.from(8f, Color.BLUE))
            )

            // RouteLineSegment 생성 - 핀 위치를 경로로 연결
            val segment = RouteLineSegment.from(
                Arrays.asList(
                    LatLng.from(37.626347381443864, 127.0928505722139),
                    LatLng.from(37.62655199375479, 127.09330961495466),
                    LatLng.from(37.62696892541316, 127.09302409993029),
                    LatLng.from(37.62752591750924, 127.09221765614906),
                    LatLng.from(37.62785649159762, 127.08999771732479),
                    LatLng.from(37.628635792096446, 127.09006662752111),
                    LatLng.from(37.62885613457392, 127.09058800230274)
                )
            ).setStyles(stylesSet.getStyles(0))

            // RouteLineOptions 생성
            val options = RouteLineOptions.from(segment)
                .setStylesSet(stylesSet)

            // RouteLineLayer에 추가하여 새로운 RouteLine 생성
            currentRouteLine = layer.addRouteLine(options)
        }
        binding.image2.setOnClickListener {
            handleImageClick(binding.image2, R.drawable.ic_tour_air, R.drawable.ic_tour_air2)
            if (!::pinManager.isInitialized) { return@setOnClickListener }

            // 모든 핀 삭제
            pinManager.removeAllPins()

            // 카메라 위치 이동
            setLocation()

            // 공강 투어 핀 추가
            pinManager.addPin(37.62855601126201, 127.09129566357015, R.drawable.icon_study2, "학습공간", "리딩라운지")
            pinManager.addPin(37.62852947084557, 127.09066124045721, R.drawable.icon_market2, "편의시설", "SWEET U")
            pinManager.addPin(37.62846592149489, 127.09128422497902, R.drawable.icon_market2, "편의시설", "미디어룸")
            pinManager.addPin(37.62611959005892, 127.09322128212314, R.drawable.icon_market2, "편의시설", "구내서점")

            // RouteLineLayer 가져오기
            val layer = kakaoMap.routeLineManager!!.layer

            // 기존 경로 삭제 (remove() 또는 layer.clear() 활용)
            currentRouteLine?.remove()
            layer.removeAll()

            // RouteLineStylesSet 생성하기 (파란색 스타일)
            val stylesSet = RouteLineStylesSet.from(
                "blueStyles",
                RouteLineStyles.from(RouteLineStyle.from(8f, Color.BLUE))
            )

            val segment = RouteLineSegment.from(
                Arrays.asList(
                    LatLng.from(37.626347381443864, 127.0928505722139),
                    LatLng.from(37.62655199375479, 127.09330961495466),
                    LatLng.from(37.62696892541316, 127.09302409993029),
                    LatLng.from(37.62752591750924, 127.09221765614906),
                    LatLng.from(37.62785649159762, 127.08999771732479),
                    LatLng.from(37.628635792096446, 127.09006662752111),
                    LatLng.from(37.62885613457392, 127.09058800230274)
                )
            ).setStyles(stylesSet.getStyles(0))

            // RouteLineOptions 생성
            val options = RouteLineOptions.from(segment)
                .setStylesSet(stylesSet)

            // RouteLineLayer에 추가하여 새로운 RouteLine 생성
            currentRouteLine = layer.addRouteLine(options)
        }

        binding.image3.setOnClickListener {
            handleImageClick(binding.image3, R.drawable.ic_tour_zz, R.drawable.ic_tour_zz2)
            if (!::pinManager.isInitialized) {
                return@setOnClickListener }
            // 모든 핀 삭제
            pinManager.removeAllPins()
            // 벼락치기 투어
            pinManager.addPin(37.627626246037536, 127.09064315094663, R.drawable.icon_cafe2, "카페/베이커리", "카페 팬도로시"); // 카페 팬도로시
            pinManager.addPin(37.62852947084557, 127.09066124045721, R.drawable.icon_market2, "편의시설", "SWEET U"); // SWEET U
            pinManager.addPin(37.62846158944593, 127.09106048389674, R.drawable.icon_market2, "편의시설", "멀티플렉스존"); // 멀티플렉스존
            pinManager.addPin(37.62885625188801, 127.09043506831497, R.drawable.icon_food2, "음식점", "버거ING"); // 버거ING
        }
        binding.image4.setOnClickListener {
            handleImageClick(binding.image4, R.drawable.ic_tour_store, R.drawable.ic_tour_store2)
            if (!::pinManager.isInitialized) { return@setOnClickListener }
            // 모든 핀 삭제
            pinManager.removeAllPins()
            // 편의점 투어
            pinManager.addPin(37.62647582535639, 127.09278276343268, R.drawable.icon_store2, "편의점", "CU 편의점"); // CU 편의점
            pinManager.addPin(37.62883702662517, 127.0890586381962, R.drawable.icon_store2, "편의점", "세븐일레븐 편의점"); // 세븐일레븐 편의점
            pinManager.addPin(37.62833893940938, 127.092368761627, R.drawable.icon_store2, "편의점", "GS25 편의점"); // GS25 편의점
            pinManager.addPin(37.62884700720883, 127.09074092533572, R.drawable.icon_store2, "편의점", "누리스토어"); // 누리스토어
        }
        binding.image5.setOnClickListener { handleImageClick(binding.image5, R.drawable.ic_tour_zz2, R.drawable.ic_tour_cafe2)
            if (!::pinManager.isInitialized) {
                return@setOnClickListener }
            // 모든 핀 삭제
            pinManager.removeAllPins()
            // 카페 투어
            pinManager.addPin(37.62858586289631, 127.09055652113533, R.drawable.icon_cafe2, "카페/베이커리", "퀴즈노스 서울여대점"); // 퀴즈노스 서울여대점
            pinManager.addPin(37.626029518338456, 127.09318718556041, R.drawable.icon_cafe2, "카페/베이커리", "카페ING"); // 카페ING
            pinManager.addPin(37.62881539645281, 127.09084001082321, R.drawable.icon_cafe2, "카페/베이커리", "에땅"); // 에땅
            pinManager.addPin(37.627626246037536, 127.09064315094663, R.drawable.icon_cafe2, "카페/베이커리", "카페 팬도로시"); // 카페 팬도로시
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
                setLocation()

                // 초기 맵 핀 설정
                setInitialPins()

                // 버튼
                setupButtonActions()
            }
        })
    }

    private fun setLocation() {
        val latitude = 37.62686435849172
        val longitude = 127.09136156335997

        // 카메라 이동 및 설정
        val cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(latitude, longitude))

        kakaoMap.moveCamera(cameraUpdate, CameraAnimation.from(500, true, true)) // 애니메이션 적용
        kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(LatLng.from(latitude, longitude)))
        kakaoMap.moveCamera(CameraUpdateFactory.zoomTo(16)) // 줌 레벨 적용
        kakaoMap.moveCamera(CameraUpdateFactory.tiltTo(Math.toRadians(0.0))) // 기울기 설정
        kakaoMap.moveCamera(CameraUpdateFactory.rotateTo(Math.toRadians(0.0))) // 회전 설정
        // 카메라 상태 확인 로그
        Log.d("KakaoMap", "Camera position: ${kakaoMap.cameraPosition}")
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
        pinManager.addPin(37.62885613457392, 127.09058800230274, R.drawable.icon_food, "음식점", "츄츄바앤츄밥"); // 츄츄바앤츄밥
        pinManager.addPin(37.62659059850936, 127.09291317936346, R.drawable.icon_food, "음식점", "교직원 식당"); // 교직원 식당
        pinManager.addPin(37.626347381443864, 127.0928505722139, R.drawable.icon_food, "음식점", "감탄떡볶이"); // 감탄떡볶이
        pinManager.addPin(37.62622118326326, 127.09292687959817, R.drawable.icon_food, "음식점", "오니기리와이규동"); // 오니기리와이규동
        pinManager.addPin(37.62885625188801, 127.09043506831497, R.drawable.icon_food, "음식점", "버거ING"); // 버거ING
        pinManager.addPin(37.62880664558206, 127.09050297888797, R.drawable.icon_food, "음식점", "더큰도시락"); // 더큰도시락
        pinManager.addPin(37.62857355250948, 127.08897336072911, R.drawable.icon_food, "음식점", "기숙사식당"); // 기숙사식당
        pinManager.addPin(37.628687040925726, 127.09079454088958, R.drawable.icon_food, "음식점", "구시아"); // 구시아

        // 주차/셔틀
        pinManager.addPin(37.62712879896861, 127.09308943627939, R.drawable.icon_bus, "주차/셔틀", "셔틀버스"); // 셔틀버스

        // 카페/베이커리
        pinManager.addPin(37.62858586289631, 127.09055652113533, R.drawable.icon_cafe, "카페/베이커리", "퀴즈노스 서울여대점"); // 퀴즈노스 서울여대점
        pinManager.addPin(37.62611302036721, 127.09298338513028, R.drawable.icon_cafe, "카페/베이커리", "뚜레쥬르"); // 뚜레쥬르
        pinManager.addPin(37.626029518338456, 127.09318718556041, R.drawable.icon_cafe, "카페/베이커리", "카페ING"); // 카페ING
        pinManager.addPin(37.62851378841688, 127.09055076948754, R.drawable.icon_cafe, "카페/베이커리", "비틀주스"); // 비틀주스
        pinManager.addPin(37.62879745737408, 127.09073520087216, R.drawable.icon_cafe, "카페/베이커리", "컴포즈커피"); // 컴포즈커피
        pinManager.addPin( 37.628245217323375, 127.09123580951733, R.drawable.icon_cafe, "카페/베이커리", "카페 딕셔너리"); // 카페 딕셔너리
        pinManager.addPin(37.62881539645281, 127.09084001082321, R.drawable.icon_cafe, "카페/베이커리", "에땅"); // 에땅
        pinManager.addPin(37.627626246037536, 127.09064315094663, R.drawable.icon_cafe, "카페/베이커리", "카페 팬도로시"); // 카페 팬도로시

        // 편의시설
        pinManager.addPin(37.6260586839787, 127.09333448635181, R.drawable.icon_market, "편의시설", "카피웍스 복사실"); // 카피웍스 복사실
        pinManager.addPin(37.62852947084557, 127.09066124045721, R.drawable.icon_market, "편의시설", "SWEET U"); // SWEET U
        pinManager.addPin(37.62611959005892, 127.09322128212314, R.drawable.icon_market, "편의시설", "구내서점"); // 구내서점
        pinManager.addPin(37.62876155285828, 127.09055956640816, R.drawable.icon_market, "편의시설", "보건실"); // 보건실
        pinManager.addPin(37.62846158944593, 127.09106048389674, R.drawable.icon_market, "편의시설", "멀티플렉스존"); // 멀티플렉스존
        pinManager.addPin(37.62846592149489, 127.09128422497902, R.drawable.icon_market, "편의시설", "미디어룸"); // 미디어룸

        // 편의점
        pinManager.addPin(37.62647582535639, 127.09278276343268, R.drawable.icon_store, "편의점", "CU 편의점"); // CU 편의점
        pinManager.addPin(37.62883702662517, 127.0890586381962, R.drawable.icon_store, "편의점", "세븐일레븐 편의점"); // 세븐일레븐 편의점
        pinManager.addPin(37.62833893940938, 127.092368761627, R.drawable.icon_store, "편의점", "GS25 편의점"); // GS25 편의점
        pinManager.addPin(37.62884700720883, 127.09074092533572, R.drawable.icon_store, "편의점", "누리스토어"); // 누리스토어

        // 학과사무실
        pinManager.addPin(37.62925489443627, 127.0904921939158, R.drawable.icon_office, "학과사무실", "정보보호학과"); // 정보보호학과
        pinManager.addPin(37.62822388842327, 127.09259235431496, R.drawable.icon_office, "학과사무실", "교육심리학과"); // 교육심리학과

        // 학습공간
        pinManager.addPin(37.62855601126201, 127.09129566357015, R.drawable.icon_study, "학습공간", "리딩라운지"); // 리딩라운지
        pinManager.addPin(37.62932261153444, 127.09030535535916, R.drawable.icon_study, "학습공간", "러닝커먼스"); // 러닝커먼스
        pinManager.addPin(37.628228511041456, 127.09244225929751, R.drawable.icon_study, "학습공간", "스터디룸"); // 스터디룸
        pinManager.addPin(37.628443477882534, 127.09117940990961, R.drawable.icon_study, "학습공간", "세미나실"); // 세미나실

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
            pinManager.addPin(37.62885613457392, 127.09058800230274, R.drawable.icon_food2, "음식점", "츄츄바앤츄밥"); // 츄츄바앤츄밥
            pinManager.addPin(37.62659059850936, 127.09291317936346, R.drawable.icon_food2, "음식점", "교직원 식당"); // 교직원 식당
            pinManager.addPin(37.626347381443864, 127.0928505722139, R.drawable.icon_food2, "음식점", "감탄떡볶이"); // 감탄떡볶이
            pinManager.addPin(37.62622118326326, 127.09292687959817, R.drawable.icon_food2, "음식점", "오니기리와이규동"); // 오니기리와이규동
            pinManager.addPin(37.62885625188801, 127.09043506831497, R.drawable.icon_food2, "음식점", "버거ING"); // 버거ING
            pinManager.addPin(37.62880664558206, 127.09050297888797, R.drawable.icon_food2, "음식점", "더큰도시락"); // 더큰도시락
            pinManager.addPin(37.62857355250948, 127.08897336072911, R.drawable.icon_food2, "음식점", "기숙사식당"); // 기숙사식당
            pinManager.addPin(37.628687040925726, 127.09079454088958, R.drawable.icon_food2, "음식점", "구시아"); // 구시아
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
            pinManager.addPin(37.62647582535639, 127.09278276343268, R.drawable.icon_store2, "편의점", "CU 편의점"); // CU 편의점
            pinManager.addPin(37.62883702662517, 127.0890586381962, R.drawable.icon_store2, "편의점", "세븐일레븐 편의점"); // 세븐일레븐 편의점
            pinManager.addPin(37.62833893940938, 127.092368761627, R.drawable.icon_store2, "편의점", "GS25 편의점"); // GS25 편의점
            pinManager.addPin(37.62884700720883, 127.09074092533572, R.drawable.icon_store2, "편의점", "누리스토어"); // 누리스토어
        }

        binding.layoutoffice.setOnClickListener {
            if (!::pinManager.isInitialized) {
                Log.e("MapFragment", "PinManager is not initialized")
                return@setOnClickListener
            }
            // 모든 핀 삭제
            pinManager.removeAllPins()

            // 학과사무실
            pinManager.addPin(37.62925489443627, 127.0904921939158, R.drawable.icon_office2, "학과사무실", "정보보호학과"); // 정보보호학과
            pinManager.addPin(37.62822388842327, 127.09259235431496, R.drawable.icon_office2, "학과사무실", "교육심리학과"); // 교육심리학과
        }

        binding.layoutmarket.setOnClickListener {
            if (!::pinManager.isInitialized) {
                Log.e("MapFragment", "PinManager is not initialized")
                return@setOnClickListener
            }
            // 모든 핀 삭제
            pinManager.removeAllPins()

            // 편의시설
            pinManager.addPin(37.6260586839787, 127.09333448635181, R.drawable.icon_market2, "편의시설", "카피웍스 복사실"); // 카피웍스 복사실
            pinManager.addPin(37.62852947084557, 127.09066124045721, R.drawable.icon_market2, "편의시설", "SWEET U"); // SWEET U
            pinManager.addPin(37.62611959005892, 127.09322128212314, R.drawable.icon_market2, "편의시설", "구내서점"); // 구내서점
            pinManager.addPin(37.62876155285828, 127.09055956640816, R.drawable.icon_market2, "편의시설", "보건실"); // 보건실
            pinManager.addPin(37.62846158944593, 127.09106048389674, R.drawable.icon_market2, "편의시설", "멀티플렉스존"); // 멀티플렉스존
            pinManager.addPin(37.62846592149489, 127.09128422497902, R.drawable.icon_market2, "편의시설", "미디어룸"); // 미디어룸

        }

        binding.layoutstudy.setOnClickListener {
            if (!::pinManager.isInitialized) return@setOnClickListener
            pinManager.removeAllPins()

            // 학습공간
            pinManager.addPin(37.62855601126201, 127.09129566357015, R.drawable.icon_study2, "학습공간", "리딩라운지"); // 리딩라운지
            pinManager.addPin(37.62932261153444, 127.09030535535916, R.drawable.icon_study2, "학습공간", "러닝커먼스"); // 러닝커먼스
            pinManager.addPin(37.628228511041456, 127.09244225929751, R.drawable.icon_study2, "학습공간", "스터디룸"); // 스터디룸
            pinManager.addPin(37.628443477882534, 127.09117940990961, R.drawable.icon_study2, "학습공간", "세미나실"); // 세미나실
        }

        binding.layoutclub.setOnClickListener {
            if (!::pinManager.isInitialized) return@setOnClickListener
            pinManager.removeAllPins()

            // 동아리
            pinManager.addPin(37.62866684063515, 127.09070105680749, R.drawable.icon_club2, "동아리", "동아리"); // 동아리
        }

        binding.layoutbank.setOnClickListener {
            if (!::pinManager.isInitialized) return@setOnClickListener
            pinManager.removeAllPins()

            // 은행
            pinManager.addPin(37.62869873773617, 127.09022813360964, R.drawable.icon_bank2, "은행", "우리은행"); // 우리은행
        }

        binding.layoutbus.setOnClickListener {
            if (!::pinManager.isInitialized) return@setOnClickListener
            pinManager.removeAllPins()

            // 주차/셔틀
            pinManager.addPin(37.62712879896861, 127.09308943627939, R.drawable.icon_bus2, "주차/셔틀", "셔틀버스"); // 셔틀버스
        }

        binding.layoutcafe.setOnClickListener {
            if (!::pinManager.isInitialized) return@setOnClickListener
            pinManager.removeAllPins()

            // 카페/베이커리
            pinManager.addPin(37.62858586289631, 127.09055652113533, R.drawable.icon_cafe2, "카페/베이커리", "퀴즈노스 서울여대점"); // 퀴즈노스 서울여대점
            pinManager.addPin(37.62611302036721, 127.09298338513028, R.drawable.icon_cafe2, "카페/베이커리", "뚜레쥬르"); // 뚜레쥬르
            pinManager.addPin(37.626029518338456, 127.09318718556041, R.drawable.icon_cafe2, "카페/베이커리", "카페ING"); // 카페ING
            pinManager.addPin(37.62851378841688, 127.09055076948754, R.drawable.icon_cafe2, "카페/베이커리", "비틀주스"); // 비틀주스
            pinManager.addPin(37.62879745737408, 127.09073520087216, R.drawable.icon_cafe2, "카페/베이커리", "컴포즈커피"); // 컴포즈커피
            pinManager.addPin( 37.628245217323375, 127.09123580951733, R.drawable.icon_cafe2, "카페/베이커리", "카페 딕셔너리"); // 카페 딕셔너리
            pinManager.addPin(37.62881539645281, 127.09084001082321, R.drawable.icon_cafe2, "카페/베이커리", "에땅"); // 에땅
            pinManager.addPin(37.627626246037536, 127.09064315094663, R.drawable.icon_cafe2, "카페/베이커리", "카페 팬도로시"); // 카페 팬도로시
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
