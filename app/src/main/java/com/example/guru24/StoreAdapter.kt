package com.example.guru24

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StoreAdapter(
    private val storeList: List<Store>,
    private val context: Context,
    private val onStoreClick: (Store, String) -> Unit
) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    class StoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val storeName: TextView = view.findViewById(R.id.storeName)
        val storeCategory: TextView = view.findViewById(R.id.storeCategory)
        val storeBuilding: TextView = view.findViewById(R.id.storeBuilding)
        val storeAddress: TextView = view.findViewById(R.id.storeAddress)
        val storePhone: TextView = view.findViewById(R.id.storePhone)
        val storeHours: TextView = view.findViewById(R.id.storeHours)
        val storeImage: ImageView = view.findViewById(R.id.storeImage)
        val storeMenu: ImageView = view.findViewById(R.id.storeMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_store, parent, false)
        return StoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = storeList[position]

        holder.storeName.text = store.name
        holder.storeCategory.text = store.category
        holder.storeBuilding.text = store.building
        holder.storeAddress.text = store.address
        holder.storePhone.text = store.phone
        holder.storeHours.text = store.hours
        holder.storeImage.setImageResource(store.image ?: R.drawable.default_image) // 기본 이미지 사용
        holder.storeMenu.setImageResource(store.menu ?: R.drawable.default_menu_image) // 기본 메뉴 이미지 사용

        // 클릭 리스너 설정
        holder.itemView.setOnClickListener {
            onStoreClick(store, store.category)
        }
    }

    override fun getItemCount(): Int = storeList.size
}
