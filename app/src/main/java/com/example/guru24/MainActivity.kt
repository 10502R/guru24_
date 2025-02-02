package com.example.guru24

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guru24.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private lateinit var storeList: List<Store>
    private lateinit var recyclerView: RecyclerView
    private lateinit var storeAdapter: StoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 가게 목록 초기화
        initializeStoreList()

        // LoginActivity에서 전달된 이메일 데이터 받기
        val email = intent.getStringExtra("USER_EMAIL")

        // 기본 Fragment 설정
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // BottomNavigationView 클릭 리스너 설정
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tabHome -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.tabMap -> {
                    replaceFragment(MapFragment())
                    true
                }
                R.id.tabTrophy -> {
                    replaceFragment(TrophyFragment())
                    true
                }
                R.id.tabMypage -> {
                    // 이메일 데이터를 MypageFragment로 전달
                    val mypageFragment = MypageFragment().apply {
                        arguments = Bundle().apply {
                            putString("USER_EMAIL", email) // 이메일 전달
                        }
                    }
                    replaceFragment(mypageFragment)
                    true
                }
                else -> false
            }
        }
    }

    // Fragment 교체 함수
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.rootlayout, fragment) // rootlayout은 FrameLayout ID
            .commit()
    }

    private fun getStoreListByCategory(category: String): List<Store> {
        return when (category) {
            "음식점" -> listOf(
                Store(
                    "츄츄바앤츄밥",
                    "음식점",
                    "학생누리관",
                    "서울 노원구 화랑로 621 105호",
                    "02-970-5378",
                    "\"월-금 11:00 - 17:30\n토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.img_chubab_1,
                    R.drawable.img_chubab_menu1
                ),
                Store(
                    "감탄떡볶이",
                    "음식점",
                    "50주년 기념관",
                    "서울 노원구 화랑로 621 50주년기념관 1층",
                    "",
                    "\"월-금 10:00 - 18:00\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.gamtan1,
                    R.drawable.gamtanmenu
                ),
                Store(
                    "더큰도시락",
                    "음식점",
                    "학생누리관",
                    "서울 노원구 화랑로 621 학생누리관 1층 108호",
                    "",
                    "\"월-금 10:00 - 18:00\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.thebig,
                    R.drawable.thebigmenu
                ),
                Store(
                    "구시아",
                    "음식점",
                    "학생누리관",
                    "서울 노원구 화랑로 621 학생누리관 B1층",
                    "",
                    "\"월-금 10:00 - 19:00\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.gusia,
                    R.drawable.gusiamenu1
                )
            )

            "카페/베이커리" -> listOf(
                Store(
                    "퀴즈노스 서울여대점",
                    "카페/베이커리",
                    "학생누리관",
                    "서울 노원구 화랑로 621 101호 (공릉동, 서울여자대학교 학생누리관)",
                    "02-977-7923",
                    "\"월-금 08:00 - 17:00\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.img_quiznos_1,
                    R.drawable.img_quiznos_menu1
                ),
                Store(
                    "카페 딕셔너리",
                    "카페/베이커리",
                    "중앙도서관",
                    "서울 노원구 화랑로 621 서울여자대학교 1층",
                    "",
                    "\"월-금 08:00 - 19:00\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.cadic,
                    R.drawable.cadicmenu1
                ),
                Store(
                    "뚜레쥬르",
                    "카페/베이커리",
                    "50주년 기념관",
                    "서울 노원구 화랑로 621 50주년기념관 1층",
                    "02-978-9776",
                    "\"월-금 07:00 - 18:00\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.tous1,
                    R.drawable.tousmenu
                ),
                Store(
                    "카페ING",
                    "카페/베이커리",
                    "50주년 기념관",
                    "서울 노원구 화랑로 621 50주년기념관 1층 101호",
                    "0507-1351-4829",
                    "\"월-금 08:00 - 19:00\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.cafeing1,
                    R.drawable.cadicmenu1
                ),
                Store(
                    "카페 팬도로시",
                    "카페/베이커리",
                    "대강당",
                    "서울 노원구 화랑로 621 서울여대 대강당 B1층",
                    "02-971-3456",
                    "\"월-금 08:30 - 19:00\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.pan1,
                    R.drawable.panmenu1
                ),
                Store(
                    "에땅",
                    "카페/베이커리",
                    "학생누리관",
                    "서울 노원구 화랑로 621 학생누리관 B1층",
                    "02-970-5388",
                    "\"월-금 10:00 - 17:00\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.ethang,
                    R.drawable.ethangmenu
                )
            )

            "편의점" -> listOf(
                Store(
                    "CU 편의점",
                    "편의점",
                    "50주년 기념관",
                    "서울 노원구 화랑로 621 50주년기념관 1층",
                    "",
                    "\"월-금 09:00 - 18:00\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.cu1,
                    R.drawable.cu2
                ),
                Store(
                    "세븐일레븐 편의점",
                    "편의점",
                    "샬롬하우스",
                    "서울 노원구 화랑로 621 샬롬기숙사 1층",
                    "02-970-5385",
                    "24시간 무인운영",
                    R.drawable.seven1,
                    R.drawable.seven2

                ),
                Store(
                    "GS25 편의점",
                    "편의점",
                    "인문사회관",
                    "서울 노원구 화랑로 621 인문사회관 1층",
                    "",
                    "\"월-금 08:00 - 19:00\n" +
                            "이후 무인운영\"\n",
                    R.drawable.gs1,
                    R.drawable.gs2
                ),
                Store(
                    "누리스토어",
                    "편의점",
                    "학생누리관",
                    "서울 노원구 화랑로 621 학생누리관 1층",
                    "",
                    "\"월-금 08:30 - 18:00\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.nuri1,
                    R.drawable.nurimenu1
                )
            )


            "편의시설" -> listOf(
                Store(
                    "카피웍스 복사실",
                    "편의시설",
                    "50주년 기념관",
                    "서울 노원구 화랑로 621 50주년기념관 B101 카피웍스",
                    "02-970-5389",
                    "\"월-금 09:00 - 18:00\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.copy1,
                    R.drawable.copy2
                ),

                Store(
                    "SWEET U",
                    "편의시설",
                    "학생누리관",
                    "서울 노원구 화랑로 621 학생누리관 1층 110호",
                    "02-970-5774",
                    "\"월-금 10:00 - 17:30\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.sweet1,
                    R.drawable.sweet2
                ),
                Store(
                    "구내서점",
                    "편의시설",
                    "50주년 기념관",
                    "서울 노원구 화랑로 621 50주년기념관 B1층",
                    "02-970-5386",
                    "\"월-금 08:30 - 18:00\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.book1,
                    R.drawable.book2
                ),
                Store(
                    "설화방",
                    "편의시설",
                    "조형예술관",
                    "서울 노원구 화랑로 621 지층 B105호 조형예술관",
                    "02-970-5391",
                    "\"월-금 09:00 - 17:00\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.seol1,
                    R.drawable.seol2
                ),
                Store(
                    "보건실",
                    "편의시설",
                    "학생누리관",
                    "서울 노원구 화랑로 621 학생누리관 202호",
                    "02-970-5075",
                    "\"월-금 09:00 - 17:30\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.hos1,
                    R.drawable.hos2
                ),
                Store(
                    "미디어룸",
                    "편의시설",
                    "중앙도서관",
                    "서울 노원구 화랑로 621 중앙도서관 1층",
                    "02-970-5851",
                    "\"월-금 09:00 - 19:30\n" +
                            "토, 일, 공휴일 정기 휴무\"",
                    R.drawable.med1,
                    R.drawable.med2
                ),
                Store(
                    "멀티플렉스존",
                    "편의시설",
                    "중앙도서관",
                    "서울 노원구 화랑로 621 중앙도서관 1층",
                    "02-970-5851",
                    "24시간 이용 가능",
                    R.drawable.mul1,
                    R.drawable.mul2
                ),
                Store(
                    "러닝커머스",
                    "편의시설",
                    "제 2과학관",
                    "서울 노원구 화랑로 621 제 2과학관 1층, B102호",
                    "",
                    "월-금 08:00 - 23:50",
                    R.drawable.run1,
                    R.drawable.run2
                )

            )


            "학습 공간" -> listOf(
                Store(
                    "리딩라운지",
                    "학습 공간",
                    "중앙도서관",
                    "서울 노원구 화랑로 621 중앙도서관 2층",
                    "02-970-5851",
                    "\"월-금 09:00 - 20:00\n" +
                            "토, 일, 공휴일 정기 휴무\"",
                    R.drawable.read1,
                    R.drawable.read2
                ),
                Store(
                    "세미나실",
                    "학습 공간",
                    "중앙도서관",
                    "서울 노원구 화랑로 621 중앙도서관 2층",
                    "02-970-5851",
                    "\"월-금 09:00 - 19:30\n" +
                            "토, 일, 공휴일 정기 휴무\"",
                    R.drawable.sem1,
                    R.drawable.sem2
                ),
                Store(
                    "스터디룸",
                    "학습 공간",
                    "인문사회관",
                    "서울 노원구 화랑로 621 인문사회관 2층",
                    "02-970-5037",
                    "\"월-금 09:00 - 22:00\n" +
                            "토, 일, 공휴일 정기 휴무\"",
                    R.drawable.stu1,
                    R.drawable.stu2
                )

            )

            "동아리" -> listOf(
                Store(
                    "소리마당",
                    "동아리",
                    "학생누리관",
                    "서울 노원구 화랑로 621 학생누리관 403B호",
                    "02-970-5371",
                    "월 18:00 - 20:00\n",
                    R.drawable.sori
                ),
                Store(
                    "마주보기",
                    "동아리",
                    "학생누리관",
                    "서울 노원구 화랑로 621 학생누리관 622호",
                    "02-970-5371",
                    "목 18:00 - 20:00\n",
                    R.drawable.maju
                ),
                Store(
                    "SWU FC",
                    "동아리",
                    "학생누리관",
                    "서울 노원구 화랑로 621 학생누리관 712호",
                    "02-970-5371",
                    "수 18:00 - 20:00\n",
                    R.drawable.swufc

                ),
                Store(
                    "CCC",
                    "동아리",
                    "학생누리관",
                    "서울 노원구 화랑로 621 학생누리관 706호",
                    "02-970-5371",
                    "목 18:00 - 20:00\n",
                    R.drawable.ccc

                ),
                Store(
                    "슈터플라이",
                    "동아리",
                    "학생누리관",
                    "서울 노원구 화랑로 621 학생누리관 617호",
                    "02-970-5371",
                    "목 18:00 - 20:00\n",
                    R.drawable.swuter
                ),
                Store(
                    "만화방",
                    "동아리",
                    "학생누리관",
                    "서울 노원구 화랑로 621 학생누리관 615호",
                    "02-970-5371",
                    "화 18:00 - 20:00\n",
                    R.drawable.man
                )
            )

            "은행" -> listOf(
                Store(
                    "우리은행",
                    "은행",
                    "학생누리관",
                    "서울 노원구 화랑로 621 학생누리관 1층",
                    "02-970-5382",
                    "\"월-금 09:00 - 16:00\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.woori1,
                    R.drawable.woori2
                ),

                Store(
                    "우리은행 CD기",
                    "은행",
                    "인문사회관",
                    "서울 노원구 화랑로 621 인문사회관 1층",
                    "02-970-5382",
                    "",
                    R.drawable.bank1,
                    R.drawable.bank2
                )
            )

            "주차/셔틀" -> listOf(
                Store(
                    "셔틀버스",
                    "주차/셔틀",
                    "",
                    "서울 노원구 화랑로55길 17 아가페교회",
                    "02-970-5371",
                    "목 18:00 - 20:00\n",
                    R.drawable.bus1,
                    R.drawable.bus2
                )
            )

            "학과사무실" -> listOf(
                Store(
                    "정보보호학과",
                    "학과사무실",
                    "제 2과학관",
                    "서울 노원구 화랑로 621 제 2과학관 201호",
                    "02-970-5601",
                    "\"월-금 09:00 - 17:30\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.jung1,
                    R.drawable.jung2
                ),
                Store(
                    "교육심리학과",
                    "학과사무실",
                    "인문사회관",
                    "서울 노원구 화랑로 621 인문사회관 517호",
                    "02-970-5561",
                    "\"월-금 09:00 - 17:30\n" +
                            "토, 일, 공휴일 정기 휴무\"\n",
                    R.drawable.kyo1,
                    R.drawable.kyo2
                )
            )
            else -> emptyList()
        }
    }

    // 가게 목록 초기화 메서드
    private fun initializeStoreList() {
        val categories = listOf(
            "음식점",
            "카페/베이커리",
            "편의점",
            "편의시설",
            "학습 공간",
            "동아리",
            "은행",
            "주차/셔틀",
            "학과사무실"
        )

        storeList = categories.flatMap { getStoreListByCategory(it) } // storeList 초기화
    }


    override fun onDestroy() {
        super.onDestroy()
        mBinding = null // 메모리 누수 방지
    }
}