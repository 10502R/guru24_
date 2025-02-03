package com.example.guru24

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.guru24.databinding.ItemStoreBinding

class StoreAdapter(
    private val storeList: List<Store>,
    private val context: Context,
    private val onStoreClick: (Store, String) -> Unit
) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    class StoreViewHolder(private val binding: ItemStoreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(store: Store, onStoreClick: (Store, String) -> Unit) {
            binding.storeName.text = store.name
            binding.storeCategory.text = store.category
            binding.storeBuilding.text = store.building
            binding.storeAddress.text = store.address
            binding.storePhone.text = store.phone
            binding.storeHours.text = store.hours

            // 이미지 설정
            binding.storeImage.setImageResource(store.image ?: R.drawable.default_image)
            binding.storeMenu.setImageResource(store.menu ?: R.drawable.default_menu_image)

            // 클릭 리스너 설정
            binding.root.setOnClickListener {
                onStoreClick(store, store.category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val binding = ItemStoreBinding.inflate(LayoutInflater.from(context), parent, false)
        return StoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = storeList[position]
        holder.bind(store, onStoreClick)
    }

    override fun getItemCount(): Int = storeList.size
}
