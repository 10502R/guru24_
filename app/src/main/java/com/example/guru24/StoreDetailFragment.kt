package com.example.guru24

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.guru24.Store

class StoreDetailFragment : Fragment() {

    private lateinit var storeNameTextView: TextView
    private lateinit var storeCategoryTextView: TextView
    private lateinit var storeBuildingTextView: TextView
    private lateinit var storeAddressTextView: TextView
    private lateinit var storePhoneTextView: TextView
    private lateinit var storeHoursTextView: TextView

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

        storeNameTextView = view.findViewById(R.id.storeName)
        storeCategoryTextView = view.findViewById(R.id.storeCategory)
        storeBuildingTextView = view.findViewById(R.id.storeBuilding)
        storeAddressTextView = view.findViewById(R.id.storeAddress)
        storePhoneTextView = view.findViewById(R.id.storePhone)
        storeHoursTextView = view.findViewById(R.id.storeHours)

        // 가게 정보 설정
        storeNameTextView.text = store.name
        storeCategoryTextView.text = store.category
        storeBuildingTextView.text = store.building
        storeAddressTextView.text = store.address
        storePhoneTextView.text = store.phone
        storeHoursTextView.text = store.hours

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
