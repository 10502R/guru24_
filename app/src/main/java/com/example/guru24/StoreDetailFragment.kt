package com.example.guru24

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.guru24.databinding.FragmentStoreDetailBinding

class StoreDetailFragment : Fragment() {

    private var _binding: FragmentStoreDetailBinding? = null
    private val binding get() = _binding!!

    private var store: Store? = null
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            store = it.getSerializable("store") as? Store
            category = it.getString("category")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        store?.let {
            updateUI(it)
        } ?: run {
            showErrorUI()
        }

        // 뒤로가기 버튼 클릭 리스너
        binding.backButton.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment.newInstance(category ?: "기본 카테고리")
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)

            // 현재 Fragment 제거
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 닫기 버튼 클릭 리스너
        binding.close.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun updateUI(store: Store) {
        binding.storeImage.setImageResource(store.image ?: R.drawable.default_image)
        binding.storeName.text = store.name
        binding.storeCategory.text = store.category
        binding.storeBuilding.text = store.building
        binding.storeAddress.text = store.address
        binding.storePhone.text = store.phone
        binding.storeHours.text = store.hours

        // 메뉴판 이미지 설정
        store.menu?.let { menuResId ->
            binding.storeMenu.setImageResource(menuResId)
            binding.storeMenu.visibility = View.VISIBLE
        } ?: run {
            binding.storeMenu.visibility = View.GONE
        }

        // 건물 정보 가시성 설정
        binding.storeBuilding.visibility = if (store.building.isNotEmpty()) View.VISIBLE else View.GONE
    }

    private fun showErrorUI() {
        binding.storeName.text = "가게 정보를 찾을 수 없습니다."
        binding.storeCategory.visibility = View.GONE
        binding.storeBuilding.visibility = View.GONE
        binding.storeAddress.visibility = View.GONE
        binding.storePhone.visibility = View.GONE
        binding.storeHours.visibility = View.GONE
        binding.storeImage.setImageResource(R.drawable.close)
        binding.storeMenu.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }

    companion object {
        fun newInstance(store: Store, category: String): StoreDetailFragment {
            return StoreDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("store", store)
                    putString("category", category)
                }
            }
        }
    }

}

