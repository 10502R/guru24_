package com.example.guru24

import DBHelper
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guru24.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var recentSearchAdapter: RecentSearchAdapter
    private lateinit var dbHelper: DBHelper

    // 🔹 전체 검색 리스트 (학교 내 장소, 음식점 등)
    private val allSearchItems = mutableListOf(
        "츄츄바앤츄밥", "감탄떡볶이", "더큰도시락", "구시아", "스윗유",
        "미디어룸", "리딩라운지", "구내서점", "카페 딕셔너리", "멀티플렉스존",
        "GS25", "세븐일레븐", "누리스토어", "에땅", "팬도로시",
        "퀴즈노스", "카페잉", "퀴즈노스 서울여대점", "교직원 식당",
        "CU 편의점", "뚜레쥬르", "카피웍스 복사실", "오니기리와이규동",
        "카페ING", "비틀주스", "컴포즈커피", "SWEET U", "버거ING"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this) // 🔹 DBHelper 초기화

        setupSearchView()
        setupRecyclerView()
        setupClearAllButton()
        loadRecentSearches() // 🔹 DB에서 최근 검색어 불러오기
    }

    private fun setupSearchView() {
        val searchView = binding.searchView
        searchView.queryHint = "검색어를 입력하세요"
        searchView.isFocusable = true
        searchView.isIconified = false
        searchView.clearFocus()

        // X 버튼 클릭 시 텍스트 초기화 후 유지
        searchView.setOnCloseListener {
            searchView.setQuery("", false)
            searchView.clearFocus()
            true
        }

        // 🔹 검색 이벤트 처리
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotEmpty()) {
                        dbHelper.insertSearchQuery(it) // 🔹 검색어를 DB에 저장
                        loadRecentSearches() // 🔹 DB에서 검색어 리스트 갱신
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterSearchResults(newText)
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        recentSearchAdapter = RecentSearchAdapter(allSearchItems) { selectedSearch ->
            binding.searchView.setQuery(selectedSearch, true) // 리스트에서 클릭하면 검색 실행
        }
        binding.recentSearchRecycler.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = recentSearchAdapter
        }
    }

    private fun setupClearAllButton() {
        binding.clearAllButton.setOnClickListener {
            dbHelper.clearAllSearchQueries() // DB에서 검색 기록 삭제
            loadRecentSearches()
        }
    }

    // 🔹 DB에서 최근 검색어 불러오기
    private fun loadRecentSearches() {
        val recentSearches = dbHelper.getRecentSearches()
        recentSearchAdapter.updateList(recentSearches)
        binding.recentSearchRecycler.visibility = if (recentSearches.isEmpty()) View.GONE else View.VISIBLE
    }

    // 🔹 입력된 검색어에 맞게 리스트 필터링
    private fun filterSearchResults(query: String?) {
        if (!query.isNullOrEmpty()) {
            val filteredList = allSearchItems.filter { it.contains(query, ignoreCase = true) }
            recentSearchAdapter.updateList(filteredList)
            binding.recentSearchRecycler.visibility = if (filteredList.isEmpty()) View.GONE else View.VISIBLE
        } else {
            binding.recentSearchRecycler.visibility = View.GONE
        }
    }
}
