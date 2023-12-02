package com.example.samdollarfront

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


class StoreAdapter(private val items: ArrayList<StoreData>, private val onClick: (StoreData) -> Unit) : RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: StoreAdapter.ViewHolder, position: Int)  {

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
        return StoreAdapter.ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(listener: View.OnClickListener, item: StoreData) {
            val txtname = view.findViewById<TextView>(R.id.txt_name)
            val txtaccount = view.findViewById<TextView>(R.id.txt_account)
            txtname.text = item.name
            txtaccount.text = item.account
            view.setOnClickListener(listener)
        }
    }
}

