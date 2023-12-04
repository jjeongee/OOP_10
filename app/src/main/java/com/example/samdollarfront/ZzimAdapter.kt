package com.example.samdollarfront

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ZzimAdapter(private val zzimList:ArrayList<Zzim>):RecyclerView.Adapter<ZzimAdapter.ZzimViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZzimViewHolder {
        val zzimview = LayoutInflater.from(parent.context).inflate(R.layout.zzim_list,parent,false)
        return ZzimViewHolder(zzimview)
    }

    override fun getItemCount(): Int {
       return zzimList.size
    }

    override fun onBindViewHolder(holder: ZzimViewHolder, position: Int) {
        holder.zzimstore.text = zzimList[position].zzimstore
        holder.zzimbank.text = zzimList[position].zzimbank
        holder.zzimaccount.text = zzimList[position].zzimaccount
        holder.zzimowner.text = zzimList[position].zzimowner


    }

    inner class ZzimViewHolder(zzimView: View):RecyclerView.ViewHolder(zzimView) {
        val zzimstore = zzimView.findViewById<TextView>(R.id.store_txt_name)
        val zzimbank = zzimView.findViewById<TextView>(R.id.store_txt_bank)
        val zzimaccount = zzimView.findViewById<TextView>(R.id.store_txt_account)
        val zzimowner = zzimView.findViewById<TextView>(R.id.store_txt_ownername)
    }
}