package com.example.samdollarfront

import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot

class StoreMenuAdapter(private val items: ArrayList<DataSnapshot>) :  RecyclerView.Adapter<StoreMenuAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {

        val item = items[position]

        holder.apply {
            bind(item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.store_menu, parent, false)
        return ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(item: DataSnapshot) {
            val txtmenu = view.findViewById<TextView>(R.id.store_txt_menu)
            val txtprice = view.findViewById<TextView>(R.id.store_txt_price)

            val name = item.child("name").getValue(String::class.java) ?: ""
            val price = item.child("price").getValue(String::class.java) ?: ""

            txtmenu.text = name
            txtprice.text = price
        }
    }
}