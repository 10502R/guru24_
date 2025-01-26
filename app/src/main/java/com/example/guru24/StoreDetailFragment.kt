package com.example.guru24

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class StoreDetailFragment : Fragment() {

    private lateinit var storeNameTextView: TextView
    private lateinit var storeCategoryTextView: TextView
    private lateinit var storeBuildingTextView: TextView
    private lateinit var storeAddressTextView: TextView
    private lateinit var storePhoneTextView: TextView
    private lateinit var storeHoursTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Fragment 초기화 시 전달받은 인자들을 처리할 수 있습니다.
        // 필요에 따라 여기에 추가 논리를 넣을 수 있습니다.
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_store_detail, container, false)

        // UI 요소 초기화
        storeNameTextView = view.findViewById(R.id.storeName)
        storeCategoryTextView = view.findViewById(R.id.storeCategory)
        storeBuildingTextView = view.findViewById(R.id.storeBuilding)
        storeAddressTextView = view.findViewById(R.id.storeAddress)
        storePhoneTextView = view.findViewById(R.id.storePhone)
        storeHoursTextView = view.findViewById(R.id.storeHours)


        // Arguments에서 데이터 가져오기
        arguments?.let {
            storeNameTextView.text = it.getString("storeName")
            storeCategoryTextView.text = it.getString("storeCategory")
            storeAddressTextView.text = it.getString("storeBuilding")
            storeAddressTextView.text = it.getString("storeAddress")
            storePhoneTextView.text = it.getString("storePhone")
            storeHoursTextView.text = it.getString("storeHours")

        }

        return view
    }

    companion object {
        // StoreDetailFragment 인스턴스를 생성하는 팩토리 메서드
        @JvmStatic
        fun newInstance(storeName: String, storeCategory: String, storeBuilding: String, storeAddress: String, storePhone: String, storeHours: String) =
            StoreDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("storeName", storeName)
                    putString("storeCategory", storeCategory)
                    putString("storeBuilding", storeBuilding)
                    putString("storeAddress", storeAddress)
                    putString("storePhone", storePhone)
                    putString("storeHours", storeHours)
                }
            }
    }
}
