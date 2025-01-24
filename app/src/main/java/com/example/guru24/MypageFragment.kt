package com.example.guru24

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.guru24.databinding.FragmentMypageBinding

class MypageFragment : Fragment() {

    private var binding: FragmentMypageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 뷰 바인딩 초기화
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        // ">" 아이콘 클릭 시 MypageTerms 액티비티로 이동
        binding?.mypageTerms?.setOnClickListener {
            val intent = Intent(requireContext(), MypageTerms::class.java)
            startActivity(intent)
        }

        return binding?.root // 바인딩된 뷰 반환
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null // 메모리 누수를 방지하기 위해 바인딩 객체 해제
    }
}
