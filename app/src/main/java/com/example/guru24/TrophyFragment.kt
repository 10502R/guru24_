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

    // ActivityResultLauncher for QR code scanning
    private val qrScanLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val data: Intent? = result.data
            val scanResult = data?.getStringExtra("SCAN_RESULT")
            // scanResult를 사용하여 처리
            if (scanResult != null) {
                println("QR 스캔 결과: $scanResult")
                // 여기에 스탬프 적립 로직 추가
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using ViewBinding
        _binding = FragmentTrophyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabs()

        // 기본 탭 설정
        if (savedInstanceState == null) {
            replaceFragment(StampCardFragment())
        }

        // 버튼 클릭 리스너 설정
        binding.btnScanQr.setOnClickListener {
            val intent = Intent(context, QrCodeScanActivity::class.java)
            qrScanLauncher.launch(intent) // Use ActivityResultLauncher instead of startActivityForResult
        }

        binding.btnLearnHow.setOnClickListener {
            val intent = Intent(context, StampInstructionsActivity::class.java)
            startActivity(intent)
        }

        // TabLayout 클릭 리스너 설정
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> replaceFragment(StampCardFragment())
                    1 -> replaceFragment(BadgeFragment())
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
}