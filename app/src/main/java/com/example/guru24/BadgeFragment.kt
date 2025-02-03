package com.example.guru24

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.guru24.databinding.FragmentBadgeBinding

class BadgeFragment : Fragment() {

    private var _binding: FragmentBadgeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBadgeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뱃지 상태 업데이트
        updateBadgeUI()
    }

    private fun updateBadgeUI() {
        val sharedPref = requireContext().getSharedPreferences("badge_status", Context.MODE_PRIVATE)
        val isTteokbokkiAchieved = sharedPref.getBoolean("tteokbokki", false)

        if (isTteokbokkiAchieved) {
            binding.badgeTteokbbokki.setImageResource(R.drawable.badge_tteokbokki_color)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}