package com.example.samdollarfront

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ZzimAdapter(private val zzimList:ArrayList<Zzim>):RecyclerView.Adapter<ZzimAdapter.ZzimViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZzimViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.zzim_list,parent,false)
        return ZzimViewHolder(view)
    }

    override fun getItemCount(): Int {
       return zzimList.size
    }

    override fun onBindViewHolder(holder: ZzimViewHolder, position: Int) {
        holder.zzimstore.text = zzimList[position].zzimclassstore
        holder.zzimbank.text = zzimList[position].zzimclassbank
        holder.zzimaccount.text = zzimList[position].zzimclassaccount
        holder.zzimowner.text = zzimList[position].zzimclassowner
//        val zzimlist = zzimList[position]
//        holder.bind(zzimlist)


    }

    inner class ZzimViewHolder(zzimView: View):RecyclerView.ViewHolder(zzimView) {
        val zzimstore = zzimView.findViewById<TextView>(R.id.tv_zzim_name)
        val zzimbank = zzimView.findViewById<TextView>(R.id.tv_zzim_bank)
        val zzimaccount = zzimView.findViewById<TextView>(R.id.tv_zzim_account)
        val zzimowner = zzimView.findViewById<TextView>(R.id.tv_zzim_ownername)

        //ZzimVeiwHolder는 xml의 textView를 가리키고 있기 때문에 중간 매개체 bind함수 필요
//        fun bind(zzim:Zzim){
//            zzimstore.text = zzim.zzimclassstore
//            zzimbank.text = zzim.zzimclassbank
//            zzimaccount.text = zzim.zzimclassaccount
//            zzimowner.text = zzim.zzimclassowner
//        }
    }
}