package com.example.guru24

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecentSearchAdapter(
    private var searchList: List<String>, // 🔹 MutableList 대신 List 사용
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<RecentSearchAdapter.SearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(searchList[position])
        holder.itemView.setOnClickListener { onItemClick(searchList[position]) }
    }

    override fun getItemCount(): Int = searchList.size

    fun updateList(newList: List<String>) {
        searchList = newList // 🔹 직접 할당하여 리스트 업데이트
        notifyDataSetChanged() // 🔹 변경된 데이터 적용
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)
        fun bind(text: String) {
            textView.text = text
        }
    }
}

