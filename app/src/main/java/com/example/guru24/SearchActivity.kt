package com.example.guru24

import DBHelper
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guru24.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var recentSearchAdapter: RecentSearchAdapter
    private lateinit var dbHelper: DBHelper
    private var selectedCategory: String? = null // 선택된 카테고리 저장

    // 🔹 전체 검색 리스트 (학교 내 장소, 음식점 등)
    private val allSearchItems = mutableListOf(
        "츄츄바앤츄밥", "감탄떡볶이", "더큰도시락", "구시아",
        "미디어룸", "리딩라운지", "구내서점", "카페 딕셔너리", "멀티플렉스존",
        "GS25 편의점", "세븐일레븐 편의점", "누리스토어", "카페 팬도로시",
        "에땅", "설화방", "보건실", "러닝커머스", "세미나실", "스터디룸", "퀴즈노스 서울여대점",
        "소리마당", "마주보기", "SWU FC", "CCC", "슈터플라이", "만화방",
        "우리은행", "우리은행 CD기", "셔틀버스", "정보보호학과", "교육심리학과",
        "CU 편의점", "뚜레쥬르", "카피웍스 복사실", "오니기리와이규동",
        "카페ING", "비틀주스", "컴포즈커피", "SWEET U", "버거ING"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this) // 🔹 DBHelper 초기화

        setupCategoryButtons() // 카테고리 버튼 설정
        setupSearchView()
        setupRecyclerView()
        setupClearAllButton()
        loadRecentSearches() // 🔹 DB에서 최근 검색어 불러오기
    }

    private fun setupCategoryButtons() {
        binding.searchRestaurant.setOnClickListener {
            selectedCategory = "음식점"
            updateCategoryIcons(binding.searchRestaurant, R.drawable.icon_clicked_food, binding.searchCafe, binding.searchConv, binding.searchRest, binding.searchStudy, binding.searchClub, binding.searchBank, binding.searchBus, binding.searchOffice)
        }

        binding.searchCafe.setOnClickListener {
            selectedCategory = "카페/베이커리"
            updateCategoryIcons(binding.searchCafe, R.drawable.icon_clicked_cafe, binding.searchRestaurant, binding.searchConv, binding.searchRest, binding.searchStudy, binding.searchClub, binding.searchBank, binding.searchBus, binding.searchOffice)
        }

        binding.searchConv.setOnClickListener {
            selectedCategory = "편의점"
            updateCategoryIcons(binding.searchConv, R.drawable.icon_clicked_store, binding.searchRestaurant, binding.searchCafe, binding.searchRest, binding.searchStudy, binding.searchClub, binding.searchBank, binding.searchBus, binding.searchOffice)
        }

        binding.searchRest.setOnClickListener {
            selectedCategory = "편의시설"
            updateCategoryIcons(binding.searchRest, R.drawable.icon_clicked_market, binding.searchRestaurant, binding.searchCafe, binding.searchConv, binding.searchStudy, binding.searchClub, binding.searchBank, binding.searchBus, binding.searchOffice)
        }

        binding.searchStudy.setOnClickListener {
            selectedCategory = "학습 공간"
            updateCategoryIcons(binding.searchStudy, R.drawable.icon_clicked_study, binding.searchRestaurant, binding.searchCafe, binding.searchConv, binding.searchRest, binding.searchClub, binding.searchBank, binding.searchBus, binding.searchOffice)
        }

        binding.searchClub.setOnClickListener {
            selectedCategory = "동아리"
            updateCategoryIcons(binding.searchClub, R.drawable.icon_clicked_club, binding.searchRestaurant, binding.searchCafe, binding.searchConv, binding.searchRest, binding.searchStudy, binding.searchBank, binding.searchBus, binding.searchOffice)
        }

        binding.searchBank.setOnClickListener {
            selectedCategory = "은행"
            updateCategoryIcons(binding.searchBank, R.drawable.icon_clicked_bank, binding.searchRestaurant, binding.searchCafe, binding.searchConv, binding.searchRest, binding.searchStudy, binding.searchClub, binding.searchBus, binding.searchOffice)
        }

        binding.searchBus.setOnClickListener {
            selectedCategory = "주차/셔틀"
            updateCategoryIcons(binding.searchBus, R.drawable.icon_clicked_bus, binding.searchRestaurant, binding.searchCafe, binding.searchConv, binding.searchRest, binding.searchStudy, binding.searchClub, binding.searchBank, binding.searchOffice)
        }

        binding.searchOffice.setOnClickListener {
            selectedCategory = "학과사무실"
            updateCategoryIcons(binding.searchOffice, R.drawable.icon_clicked_office, binding.searchRestaurant, binding.searchCafe, binding.searchConv, binding.searchRest, binding.searchStudy, binding.searchClub, binding.searchBank, binding.searchBus)
        }
    }

    private fun updateCategoryIcons(selectedButton: View, selectedImageResId: Int, vararg otherButtons: ImageView) {
        // 클릭된 버튼의 이미지를 변경
        (selectedButton as ImageView).setImageResource(selectedImageResId)

        // 다른 버튼들의 이미지를 원래 상태로 되돌리기
        otherButtons.forEach { button ->
            when (button.id) {
                R.id.searchRestaurant -> button.setImageResource(R.drawable.icon_unclicked_food)
                R.id.searchCafe -> button.setImageResource(R.drawable.icon_unclicked_cafe)
                R.id.searchConv -> button.setImageResource(R.drawable.icon_unclicked_store)
                R.id.searchRest -> button.setImageResource(R.drawable.icon_unclicked_market)
                R.id.searchStudy -> button.setImageResource(R.drawable.icon_unclicked_study)
                R.id.searchClub -> button.setImageResource(R.drawable.icon_unclicked_club)
                R.id.searchBank -> button.setImageResource(R.drawable.icon_unclicked_bank)
                R.id.searchBus -> button.setImageResource(R.drawable.icon_unclicked_bus)
                R.id.searchOffice -> button.setImageResource(R.drawable.icon_unclicked_office)
            }
        }
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
            //TODO 화면 이동
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("isSecondActivity", true)
            intent.putExtra("selectedSearch", selectedSearch)
            intent.putExtra("selectedCategory", selectedCategory)
            startActivity(intent)

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
