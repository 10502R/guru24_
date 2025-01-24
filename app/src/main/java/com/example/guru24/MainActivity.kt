package com.example.guru24

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.guru24.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 기본 Fragment 설정
        if (savedInstanceState == null) {
            // 앱 시작 시 기본 Fragment를 설정 (예: HomeFragment)
            replaceFragment(HomeFragment())
        }

        // BottomNavigationView 클릭 리스너 설정
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.tabHome -> {
                    supportFragmentManager.beginTransaction().replace(R.id.rootlayout, HomeFragment()).commit()
                    true
                }
                R.id.tabMap -> {
                    supportFragmentManager.beginTransaction().replace(R.id.rootlayout, MapFragment()).commit()
                    true
                }
                R.id.tabTrophy -> {
                    supportFragmentManager.beginTransaction().replace(R.id.rootlayout, TrophyFragment()).commit()
                    true
                }
                R.id.tabMypage -> {
                    supportFragmentManager.beginTransaction().replace(R.id.rootlayout, MypageFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }

    // Fragment 교체 함수
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.rootlayout, fragment) // fragmentContainer는 실제 프래그먼트를 담을 컨테이너의 ID
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null // 메모리 누수 방지
    }
}
