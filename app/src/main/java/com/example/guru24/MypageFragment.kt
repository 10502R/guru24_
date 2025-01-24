package com.example.guru24

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

        // 로그아웃 버튼 클릭 시 AlertDialog 표시
        binding?.tvLogout?.setOnClickListener {
            showLogoutDialog()
        }

        return binding?.root // 바인딩된 뷰 반환
    }

    private fun showLogoutDialog() {
        // AlertDialog 생성
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("로그아웃 할까요?")
            .setPositiveButton("취소") { dialog, _ ->
                dialog.dismiss() // 닫기
            }
            .setNegativeButton("로그아웃") { dialog, _ ->
                dialog.dismiss() // 닫기
                performLogout() // 로그아웃
            }

        // AlertDialog 표시
        builder.create().show()
    }

    private fun performLogout() {
        // SharedPreferences에서 로그인 상태 변경
        val sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", AppCompatActivity.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isLoggedIn", false).apply() // 로그인 상태를 false로 설정

        showLogoutCompleteDialog() // 로그아웃 완료
    }

    private fun showLogoutCompleteDialog() {
        // 로그아웃 완료 다이얼로그 생성
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("로그아웃이 완료되었습니다.")
            .setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss() // 확인 클릭 시 다이얼로그 닫기

                // 로그인 화면으로 이동
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }

        // AlertDialog 표시
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null // 메모리 누수를 방지하기 위해 바인딩 객체 해제
    }
}
