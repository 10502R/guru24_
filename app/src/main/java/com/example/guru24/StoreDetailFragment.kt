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
import androidx.fragment.app.FragmentManager

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
        val backButton: ImageView = view.findViewById(R.id.backButton)
        val closeButton: ImageView = view.findViewById(R.id.close)

        // UI 요소 초기화
        storeImageView = view.findViewById(R.id.storeImage)
        storeNameTextView = view.findViewById(R.id.storeName)
        storeCategoryTextView = view.findViewById(R.id.storeCategory)
        storeBuildingTextView = view.findViewById(R.id.storeBuilding)
        storeAddressTextView = view.findViewById(R.id.storeAddress)
        storePhoneTextView = view.findViewById(R.id.storePhone)
        storeHoursTextView = view.findViewById(R.id.storeHours)
        storeMenuView = view.findViewById(R.id.storeMenu)


        // Store 객체에서 정보 설정
        updateUI()

        // 클릭 리스너 설정
        backButton.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment.newInstance(category ?: "기본 카테고리") // 기본 카테고리 값 설정
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)

            // 현재 Fragment를 제거하고 이전 Fragment로 돌아갑니다.
            requireActivity().supportFragmentManager.popBackStack() // 현재 Fragment 없애기
        }

//        closeButton.setOnClickListener {
//            // MapFragment로 돌아가기
//            requireActivity().supportFragmentManager.popBackStack("MapFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE) // MapFragment로 돌아가기
//        }


        // Visibility 설정
        storeBuildingTextView.visibility = View.VISIBLE
        storeMenuView.visibility = View.VISIBLE

        return view
    }

    private fun updateUI() {
        storeImageView.setImageResource(store.image ?: R.drawable.default_image)
        storeNameTextView.text = store.name
        storeCategoryTextView.text = store.category
        storeBuildingTextView.text = store.building
        storeAddressTextView.text = store.address
        storePhoneTextView.text = store.phone
        storeHoursTextView.text = store.hours

        // 메뉴판 이미지 설정
        store.menu?.let { menuResId ->
            storeMenuView.setImageResource(menuResId)
            storeMenuView.visibility = View.VISIBLE
        } ?: run {
            storeMenuView.visibility = View.GONE
        }

        // 가시성 설정
        storeBuildingTextView.visibility = if (store.building.isNotEmpty()) View.VISIBLE else View.GONE


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
