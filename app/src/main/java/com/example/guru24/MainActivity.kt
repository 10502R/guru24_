package com.example.guru24

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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


    override fun onDestroy() {
        super.onDestroy()
        mBinding = null // 메모리 누수 방지
    }

}