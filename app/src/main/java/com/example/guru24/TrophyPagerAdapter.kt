package com.example.guru24

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TrophyPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2 // 두 개의 탭 (전체 뱃지, 달성 뱃지)

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> StampCardFragment() // 전체 뱃지 프래그먼트
            1 -> BadgeFragment() // 달성 뱃지 프래그먼트
            else -> throw IllegalStateException("Invalid tab position")
        }
    }
}
