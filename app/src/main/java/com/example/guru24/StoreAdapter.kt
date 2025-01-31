package com.example.guru24

import android.content.Context
import android.view.LayoutInflater
import com.example.guru24.Store
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StoreAdapter(
    private val storeList: List<Store>,
    private val context: Context,
    private val onStoreClick: (Store) -> Unit
) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    class StoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val storeName: TextView = itemView.findViewById(R.id.storeName)
        val storeCategory: TextView = itemView.findViewById(R.id.storeCategory)
        val storeBuilding: TextView = itemView.findViewById(R.id.storeBuilding)
        val storeAddress: TextView = itemView.findViewById(R.id.storeAddress)
        val storePhone: TextView = itemView.findViewById(R.id.storePhone)
        val storeHours: TextView = itemView.findViewById(R.id.storeHours)
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
        holder.storeImage.setImageResource(store.image)
        holder.storeMenu.setImageResource(store.menu)

        // 클릭 리스너 설정
        holder.itemView.setOnClickListener {
            onStoreClick(store) // 클릭 시 가게 정보 전달
        }
    }

    override fun getItemCount(): Int = storeList.size
}
