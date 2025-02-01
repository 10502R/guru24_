package com.example.guru24

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class StoreDetailFragment : Fragment() {

    private lateinit var storeNameTextView: TextView
    private lateinit var storeCategoryTextView: TextView
    private lateinit var storeBuildingTextView: TextView
    private lateinit var storeAddressTextView: TextView
    private lateinit var storePhoneTextView: TextView
    private lateinit var storeHoursTextView: TextView
    private lateinit var storeImageView: ImageView
    private lateinit var storeMenuView: ImageView

    private lateinit var store: Store
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            store = it.getSerializable("store") as Store
            category = it.getString("category")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_store_detail, container, false)
        val backButton: ImageButton = view.findViewById(R.id.backButton)
        val closeButton: ImageButton = view.findViewById(R.id.close)


        // 버튼 클릭 리스너 설정
        backButton.setOnClickListener {
            // 현재 Fragment를 제거하고 이전 Fragment로 돌아갑니다.
            requireActivity().supportFragmentManager.popBackStack() // 현재 Fragment 없애기

            // 이전 Fragment에서 카테고리 값을 사용하여 바텀 시트 표시
            val bottomSheetFragment = BottomSheetFragment.newInstance(category ?: "기본 카테고리") // 기본 카테고리 값 설정
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        closeButton.setOnClickListener {
            // MapFragment로 돌아가기
            requireActivity().supportFragmentManager.popBackStack("MapFragment", 0) // MapFragment로 돌아가기
        }


        // UI 요소 초기화
        val storeImageView: ImageView = view.findViewById(R.id.storeImage)
        val storeNameTextView: TextView = view.findViewById(R.id.storeName)
        val storeCategoryTextView: TextView = view.findViewById(R.id.storeCategory)
        val storeBuildingTextView: TextView = view.findViewById(R.id.storeBuilding)
        val storeAddressTextView: TextView = view.findViewById(R.id.storeAddress)
        val storePhoneTextView: TextView = view.findViewById(R.id.storePhone)
        val storeHoursTextView: TextView = view.findViewById(R.id.storeHours)
        val storeMenuView: ImageView = view.findViewById(R.id.storeMenu)

        // Store 객체에서 정보 설정
        storeImageView.setImageResource(store.image ?: R.drawable.default_image)
        storeNameTextView.text = store.name
        storeCategoryTextView.text = store.category
        storeBuildingTextView.text = store.building
        storeAddressTextView.text = store.address
        storePhoneTextView.text = store.phone
        storeHoursTextView.text = store.hours

        // 메뉴판 이미지 설정
        store.menu?.let { menuResId ->
            storeMenuView.setImageResource(menuResId) // menu가 null이 아닐 때만 설정
            storeMenuView.visibility = View.VISIBLE // 메뉴판 이미지 표시
        } ?: run {
            storeMenuView.visibility = View.GONE // menu가 null일 때 숨김
        }

//        // Visibility 설정
        storeBuildingTextView.visibility = View.VISIBLE
        storeMenuView.visibility = View.VISIBLE

        return view
    }

    companion object {
        fun newInstance(store: Store, category: String) = StoreDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable("store", store)
                putString("category", category)
            }
        }
    }
}
