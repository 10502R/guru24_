package com.example.guru24

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.guru24.databinding.FragmentTrophyBinding
import com.google.android.material.tabs.TabLayout

class TrophyFragment : Fragment() {

    private var _binding: FragmentTrophyBinding? = null
    private val binding get() = _binding!!
    private var initialTabIndex: Int = 0 // 기본 탭 인덱스 설정

    // ActivityResultLauncher for QR code scanning
    private val qrScanLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val data: Intent? = result.data
            val scanResult = data?.getStringExtra("SCAN_RESULT")
            // scanResult를 사용하여 처리
            if (scanResult != null) {
                val stampCardFragment = childFragmentManager.findFragmentByTag("StampCardFragment") as? StampCardFragment
                stampCardFragment?.onStampAcquired(scanResult)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            initialTabIndex = it.getInt("selectedTab", 1) // 전달받은 탭 인덱스
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // ViewBinding 초기화
        _binding = FragmentTrophyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabs()

        // 초기 탭 설정
        if (savedInstanceState == null) {
            when (initialTabIndex) {
                0 -> replaceFragment(StampCardFragment(), "StampCardFragment")
                1 -> replaceFragment(BadgeFragment(), "BadgeFragment")
            }
            binding.tabLayout.getTabAt(initialTabIndex)?.select() // 초기 탭 선택
        }

        // QR 코드 스캔 버튼 리스너
        binding.btnScanQr.setOnClickListener {
            val intent = Intent(context, QrCodeScanActivity::class.java)
            qrScanLauncher.launch(intent)
        }

        // 학습 방법 보기 버튼 리스너
        binding.btnLearnHow.setOnClickListener {
            val intent = Intent(context, StampInstructionsActivity::class.java)
            startActivity(intent)
        }

        // TabLayout 클릭 리스너 설정
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> replaceFragment(StampCardFragment(), "StampCardFragment")
                    1 -> replaceFragment(BadgeFragment(), "BadgeFragment")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupTabs() {
        // 동적으로 count 값을 전달 (예: 스탬프 카운트와 뱃지 카운트)
        val stampCount = "5" // 예시 값
        val badgeCount = "0" // 예시 값

        val stampTab = binding.tabLayout.newTab().apply {
            customView = createTabView(getString(R.string.tab_stamp_card), stampCount)
        }
        val badgeTab = binding.tabLayout.newTab().apply {
            customView = createTabView(getString(R.string.tab_badge), badgeCount)
        }
        binding.tabLayout.addTab(stampTab)
        binding.tabLayout.addTab(badgeTab)
    }

    private fun createTabView(title: String, count: String): View {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_tab, binding.tabLayout, false)
        view.findViewById<TextView>(R.id.tab_title).text = title
        return view
    }

    // Fragment 교체 함수
    private fun replaceFragment(fragment: Fragment, tag: String) {
        childFragmentManager.beginTransaction()
            .replace(R.id.tab_layout_container, fragment, tag)
            .commit()
    }

    fun navigateToBadgeFragment() {
        // BadgeFragment로 이동 및 탭 선택
        replaceFragment(BadgeFragment(), "BadgeFragment")
        binding.tabLayout.getTabAt(1)?.select()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }

    companion object {
        fun newInstance(selectedTab: Int): TrophyFragment {
            return TrophyFragment().apply {
                arguments = Bundle().apply {
                    putInt("selectedTab", selectedTab)
                }
            }
        }
    }
}
