package com.example.guru24

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.guru24.databinding.FragmentMypageBinding

class MypageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 뷰 바인딩 초기화
        _binding = FragmentMypageBinding.inflate(inflater, container, false)

        // 전달된 이메일 정보 가져오기
        val email = arguments?.getString("USER_EMAIL")
        binding.tvID.text = email // 바인딩을 통해 TextView에 이메일 설정

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }
}
