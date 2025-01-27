package com.example.guru24

//class StoreAdapter(private val storeList: List<Store>, private val context: Context) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

//    class StoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val storeName: TextView = itemView.findViewById(R.id.storeName)
//        val storeCategory: TextView = itemView.findViewById(R.id.storeCategory)
//        val storeBuilding: TextView = itemView.findViewById(R.id.storeBuilding)
//        val storeAddress: TextView = itemView.findViewById(R.id.storeAddress)
//        val storePhone: TextView = itemView.findViewById(R.id.storePhone)
//        val storeHours: TextView = itemView.findViewById(R.id.storeHours)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
//        val view = LayoutInflater.from(context).inflate(R.layout.item_store, parent, false)
//        return StoreViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
//        val store = storeList[position]
//        holder.storeName.text = store.name
//        holder.storeCategory.text = store.category
//        holder.storeBuilding.text = store.building
//        holder.storeAddress.text = store.address
//        holder.storePhone.text = store.phone
//        holder.storeHours.text = store.hours
//
//
//        holder.itemView.setOnClickListener {
//            // Fragment로 데이터 전달
//            val fragment = StoreDetailFragment.newInstance(
//                store.name,
//                store.category,
//                store.building,
//                store.address,
//                store.phone,
//                store.hours
//            )
//
//            // Fragment 전환
//            (context as AppCompatActivity).supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, fragment) // 'fragment_container'는 Fragment가 표시될 View ID
//                .addToBackStack(null)
//                .commit()
//        }
//    }
//
//    override fun getItemCount(): Int = storeList.size
//}
