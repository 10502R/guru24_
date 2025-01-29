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
    private val recentSearches = mutableListOf("Java", "Python", "C++", "Android", "SQL", "JavaScript")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ binding을 먼저 초기화
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ 이제 binding이 초기화되었으므로, SearchView 내부 EditText 접근 가능
        val searchEditText = binding.searchView.findViewById<android.widget.EditText>(
            androidx.appcompat.R.id.search_src_text
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
