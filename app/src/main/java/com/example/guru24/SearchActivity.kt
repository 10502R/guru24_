package com.example.guru24

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guru24.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var recentSearchAdapter: RecentSearchAdapter
    private val recentSearches = mutableListOf("츄츄바앤츄밥", "감탄떡볶이", "더큰도시락", "구시아", "스윗유",
        "미디어룸", "리딩라운지", "구내서점", "카페 딕셔너리", "멀티플렉스존", "GS25", "세븐일레븐",
        "누리스토어", "에땅", "팬도로시", "퀴즈노스", "카페잉", "퀴즈노스 서울여대점", "교직원 식당",
        "CU 편의점", "뚜레쥬르", "카피웍스 복사실", "오니기리와이규동", "카페ING", "비틀주스",
        "컴포즈커피", "SWEET U", "버거ING", "기숙사식당", "세븐일레븐 편의점", "카페 팬도로시",
        "GS25 편의점", "설화방", "우리은행", "우리은행 CD기", "셔틀버스", "극예술연구회", "소리마당",
        "마주보기", "캐슈넛", "스키부", "SWU FC", "불교학생회 세주", "CCC", "무한비트", "슈터플라이",
        "만화방", "서우회", "보건실", "정보보호학과", "교육심리학과"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchEditText = binding.searchView.findViewById<android.widget.EditText>(
            R.id.search_text
        )
        searchEditText.setTextColor(resources.getColor(android.R.color.black))
        searchEditText.setHintTextColor(resources.getColor(android.R.color.darker_gray))
        searchEditText.textSize = 16f
        searchEditText.isFocusable = true
        searchEditText.requestFocus()

        // RecyclerView 초기화
        recentSearchAdapter = RecentSearchAdapter(recentSearches) { position ->
            removeSearchItem(position)
        }
        binding.recentSearchRecycler.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = recentSearchAdapter
        }

        // SearchView 설정 및 로그 추가
        binding.searchView.queryHint = "검색어를 입력하세요"
        binding.searchView.isFocusable = true
        binding.searchView.isIconified = false
        binding.searchView.requestFocus()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("SearchActivity", "검색 실행됨: $query") // ✅ 로그 추가
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("SearchActivity", "검색어 입력 중: $newText") // ✅ 로그 추가
                filterSearchResults(newText)
                return false
            }
        })

        // 전체 삭제 버튼
        binding.clearAllButton.setOnClickListener {
            clearAllSearches()
        }
    }

    private fun filterSearchResults(query: String?) {
        if (!query.isNullOrEmpty()) {
            val filteredList = recentSearches.filter { it.contains(query, ignoreCase = true) }
            recentSearchAdapter.updateList(filteredList)
            binding.recentSearchRecycler.visibility = if (filteredList.isEmpty()) View.GONE else View.VISIBLE
        } else {
            recentSearchAdapter.updateList(recentSearches)
            binding.recentSearchRecycler.visibility = View.VISIBLE
        }
    }

    private fun removeSearchItem(position: Int) {
        if (position in recentSearches.indices) {
            recentSearches.removeAt(position)
            recentSearchAdapter.updateList(recentSearches)
        }
    }

    private fun clearAllSearches() {
        recentSearches.clear()
        recentSearchAdapter.updateList(recentSearches)
    }
}
