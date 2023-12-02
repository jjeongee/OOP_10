package com.example.samdollarfront

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class StoreAdapter(val context: Context, val items: ArrayList<StoreData>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_view, null)
        val name = view.findViewById<TextView>(R.id.listview_name)
        val account = view.findViewById<TextView>(R.id.listview_account)

        val item = items[position]
        name.text = item.name
        account.text = item.account

        return view
    }

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = items.size
}