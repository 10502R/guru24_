package com.example.guru24

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var storeAdapter: StoreAdapter
    private lateinit var storeList: List<Store>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.bottomSheetRecyclerView)
        storeList = getStoreList() // 목록을 가져오는 메서드 호출
        storeAdapter = StoreAdapter(storeList, requireContext()) { store ->
            // 가게 이름 클릭 시 상세 페이지로 이동
            val fragment = StoreDetailFragment.newInstance(store)
            (requireActivity() as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = storeAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getStoreList(): List<Store> {
        return listOf(
            Store("퀴즈노스 서울여대점", "카페/베이커리", "학생누리관", "서울 노원구 화랑로 621 101호 (공릉동, 서울여자대학교 학생누리관)",
                "02-977-7923", "\"월-금 08:00 - 17:00\n" +
                        "토, 일, 공휴일 정기 휴무\"\n"),
            Store("츄츄바앤츄밥", "음식점", "학생누리관", "서울 노원구 화랑로 621 105호", "02-970-5378", "\"월-금 11:00 - 17:30\n" +
                    "토, 일, 공휴일 정기 휴무\"\n"),
            Store("카페 딕셔너리", "카페/베이커리", "중앙도서관", "서울 노원구 화랑로 621 서울여자대학교 1층", "", "\"월-금 08:00 - 19:00\n" +
                    "토, 일, 공휴일 정기 휴무\"\n"),
            Store("CU 편의점", "편의점", "50주년 기념관", "서울 노원구 화랑로 621 50주년기념관 1층", "", "\"월-금 09:00 - 18:00\n" +
                    "토, 일, 공휴일 정기 휴무\"\n"),
            Store("뚜레쥬르", "카페/베이커리", "50주년 기념관", "서울 노원구 화랑로 621 50주년기념관 1층", "02-978-9776", "\"월-금 07:00 - 18:00\n" +
                    "토, 일, 공휴일 정기 휴무\"\n"),
            Store("감탄떡볶이", "음식점", "50주년 기념관", "서울 노원구 화랑로 621 50주년기념관 1층", "", "\"월-금 10:00 - 18:00\n" +
                    "토, 일, 공휴일 정기 휴무\"\n"),
            Store("카피웍스 복사실", "편의시설", "50주년 기념관", "서울 노원구 화랑로 621 50주년기념관 B101 카피웍스", "02-970-5389", "\"월-금 09:00 - 18:00\n" +
                    "토, 일, 공휴일 정기 휴무\"\n"),
            Store("카페ING", "카페/베이커리", "50주년 기념관", "서울 노원구 화랑로 621 50주년기념관 1층 101호", "0507-1351-4829", "\"월-금 08:00 - 19:00\n" +
                    "토, 일, 공휴일 정기 휴무\"\n"),
            Store("SWEET U", "편의시설", "학생누리관", "서울 노원구 화랑로 621 학생누리관 1층 110호", "02-970-5774", "\"월-금 10:00 - 17:30\n" +
                    "토, 일, 공휴일 정기 휴무\"\n"),
            Store("버거ING", "음식점", "학생누리관", "서울 노원구 화랑로 621 학생누리관 1층", "", "\"월-금 10:00 - 18:00\n" +
                    "토, 일, 공휴일 정기 휴무\"\n"),
            Store("세븐일레븐 편의점", "편의점", "샬롬하우스", "서울 노원구 화랑로 621 샬롬기숙사 1층", "02-970-5385", "24시간 무인운영"),
            Store("구내서점", "편의시설", "50주년 기념관", "서울 노원구 화랑로 621 50주년기념관 B1층", "02-970-5386", "\"월-금 08:30 - 18:00\n" +
                    "토, 일, 공휴일 정기 휴무\"\n"),
            Store("카페 팬도로시", "카페/베이커리", "대강당", "서울 노원구 화랑로 621 서울여대 대강당 B1층", "02-971-3456", "\"월-금 08:30 - 19:00\n" +
                    "토, 일, 공휴일 정기 휴무\"\n"),
            Store("GS25 편의점", "편의점", "인문사회관", "서울 노원구 화랑로 621 인문사회관 1층", "", "\"월-금 08:00 - 19:00\n" +
                    "이후 무인운영\"\n"),
            Store("설화방", "편의시설", "조형예술관", "서울 노원구 화랑로 621 지층 B105호 조형예술관", "02-970-5391", "\"월-금 09:00 - 17:00\n" +
                    "토, 일, 공휴일 정기 휴무\"\n"),
            Store("구시아", "음식점", "학생누리관", "서울 노원구 화랑로 621 학생누리관 B1층", "", "\"월-금 10:00 - 19:00\n" +
                    "토, 일, 공휴일 정기 휴무\"\n"),
            Store("우리은행", "은행", "학생누리관", "서울 노원구 화랑로 621 학생누리관 1층", "02-970-5382", "\"월-금 09:00 - 16:00\n" +
                    "토, 일, 공휴일 정기 휴무\"\n"),
            Store("우리은행 CD기", "은행", "인문사회관", "서울 노원구 화랑로 621 인문사회관 1층", "02-970-5382", ""),
            Store("셔틀버스", "주차/셔틀", "", "서울 노원구 화랑로55길 17 아가페교회", "02-970-5371", "목 18:00 - 20:00\n"),
            Store("소리마당", "동아리", "학생누리관", "서울 노원구 화랑로 621 학생누리관 403B호", "02-970-5371", "월 18:00 - 20:00\n"),
            Store("마주보기", "동아리", "학생누리관", "서울 노원구 화랑로 621 학생누리관 622호", "02-970-5371", "목 18:00 - 20:00\n"),
            Store("SWU FC", "동아리", "학생누리관", "서울 노원구 화랑로 621 학생누리관 712호", "02-970-5371", "수 18:00 - 20:00\n"),
            Store("CCC", "동아리", "학생누리관", "서울 노원구 화랑로 621 학생누리관 706호", "02-970-5371", "목 18:00 - 20:00\n"),
            Store("슈터플라이", "동아리", "학생누리관", "서울 노원구 화랑로 621 학생누리관 617호", "02-970-5371", "목 18:00 - 20:00\n"),
            Store("만화방", "동아리", "학생누리관", "서울 노원구 화랑로 621 학생누리관 615호", "02-970-5371", "화 18:00 - 20:00\n"),

            Store("보건실", "편의시설", "학생누리관", "서울 노원구 화랑로 621 학생누리관 202호", "02-970-5075", "\"월-금 09:00 - 17:30\n" +
                    "토, 일, 공휴일 정기 휴무\"\n"),
            Store("정보보호학과", "학과사무실", "제 2과학관", "서울 노원구 화랑로 621 제 2과학관 201호", "02-970-5601", "\"월-금 09:00 - 17:30\n" +
                    "토, 일, 공휴일 정기 휴무\"\n"),
            Store("교육심리학과", "학과사무실", "인문사회관", "서울 노원구 화랑로 621 인문사회관 517호", "02-970-5561", "\"월-금 09:00 - 17:30\n" +
                    "토, 일, 공휴일 정기 휴무\"\n")

        )
    }
}
