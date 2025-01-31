package com.example.guru24

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            store = it.getSerializable("store") as Store
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_store_detail, container, false)

        // UI 요소 초기화
        storeNameTextView = view.findViewById(R.id.storeName)
        storeCategoryTextView = view.findViewById(R.id.storeCategory)
        storeBuildingTextView = view.findViewById(R.id.storeBuilding)
        storeAddressTextView = view.findViewById(R.id.storeAddress)
        storePhoneTextView = view.findViewById(R.id.storePhone)
        storeHoursTextView = view.findViewById(R.id.storeHours)
        storeImageView = view.findViewById(R.id.storeImage)
        storeMenuView = view.findViewById(R.id.storeMenu)

        // 가게 정보 설정
        storeNameTextView.text = store.name
        storeCategoryTextView.text = store.category
        storeBuildingTextView.text = store.building
        storeAddressTextView.text = store.address
        storePhoneTextView.text = store.phone
        storeHoursTextView.text = store.hours
        storeImageView.setImageResource(store.image)
        storeMenuView.setImageResource(store.menu)

        return view
    }

    companion object {
        fun newInstance(store: Store) = StoreDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable("store", store)
            }
        }
    }
}
