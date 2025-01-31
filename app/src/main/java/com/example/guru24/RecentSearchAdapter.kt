package com.example.guru24

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecentSearchAdapter(
    private var searchList: MutableList<String>,
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
        searchList.clear()
        searchList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)
        fun bind(text: String) {
            textView.text = text
        }
    }
}
