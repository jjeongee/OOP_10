package com.example.samdollarfront

import android.content.Intent
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
        val zzimItem = zzimList[position]
        holder.bind(zzimItem)
    }

    inner class ZzimViewHolder(zzimView: View):RecyclerView.ViewHolder(zzimView) {
        val zzimstore = zzimView.findViewById<TextView>(R.id.store_zzim_name)
        val zzimbank = zzimView.findViewById<TextView>(R.id.store_zzim_bank)
        val zzimaccount = zzimView.findViewById<TextView>(R.id.store_zzim_account)
        val zzimowner = zzimView.findViewById<TextView>(R.id.store_zzim_ownername)

        fun bind(zzim:Zzim){
            zzimstore.text = zzim.zzimclassstore
            zzimbank.text = zzim.zzimclassbank
            zzimaccount.text = zzim.zzimclassaccount
            zzimowner.text = zzim.zzimclassowner
        }
    }
//    fun dataFromStore(dataText: String){
//        storeName = intent.getStringExtra("store_info_name")?:""
//        storeBank = intent.getStringExtra("store_info_bank")?:""
//        storeAccount = intent.getStringExtra("store_info_account")?:""
//        storeOwner = intent.getStringExtra("store_info_ownername")?:""
//    }

}