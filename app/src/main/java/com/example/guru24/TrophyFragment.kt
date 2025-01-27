package com.example.guru24

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.guru24.databinding.FragmentTrophyBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class TrophyFragment : Fragment() {

    private var _binding: FragmentTrophyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrophyBinding.inflate(inflater, container, false)
        val view = binding.root

        setupTabs()

        // 기본 탭 설정
        if (savedInstanceState == null) {
            replaceFragment(StampCardFragment())
        }

        // 버튼 클릭 리스너 설정
        binding.btnScanQr.setOnClickListener {
            val intent = Intent(context, QrCodeScanActivity::class.java)
            startActivityForResult(intent, QR_SCAN_REQUEST_CODE)
        }

        binding.btnLearnHow.setOnClickListener {
            val intent = Intent(context, StampInstructionsActivity::class.java)
            startActivity(intent)
        }

        // TabLayout 클릭 리스너 설정
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> replaceFragment(StampCardFragment())
                    1 -> replaceFragment(BadgeFragment())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        return view
    }

    private fun setupTabs() {
        val stampTab = binding.tabLayout.newTab().apply {
            customView = createTabView("스탬프 카드", "0")
        }
        val badgeTab = binding.tabLayout.newTab().apply {
            customView = createTabView("달성 뱃지", "0")
        }
        binding.tabLayout.addTab(stampTab)
        binding.tabLayout.addTab(badgeTab)
    }

    private fun createTabView(title: String, count: String): View {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null)
        view.findViewById<TextView>(R.id.tab_title).text = title
        view.findViewById<TextView>(R.id.tab_count).text = count
        return view
    }

    // Fragment 교체 함수
    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.tab_layout_container, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }

    companion object {
        const val QR_SCAN_REQUEST_CODE = 1001
    }
}
