package com.example.samdollarfront

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


class StoreAdapter(val items: ArrayList<StoreData>, val onClick: (StoreData) -> Unit) : RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  {

        val item = items[position]

        val listener = View.OnClickListener { it ->
            item.let{
                onClick(item)
            }
        }

        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(inflatedView)
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(listener: View.OnClickListener, item: StoreData) {
            val txtname = view.findViewById<TextView>(R.id.txt_name)
            val txtaccount = view.findViewById<TextView>(R.id.txt_account)
            val txtDistance = view.findViewById<TextView>(R.id.txt_distance)

            txtname.text = item.name
            txtaccount.text = item.account
            txtDistance.text = "${"%.2f".format(item.distance)}km"
            view.setOnClickListener(listener)
        }
        fun updateDistance(distance: String) {
            Log.d("UpdateDistance", "Distance Updated: $distance")
            val txtDistance = view.findViewById<TextView>(R.id.txt_distance)
            txtDistance.text = distance
        }
    }
}

